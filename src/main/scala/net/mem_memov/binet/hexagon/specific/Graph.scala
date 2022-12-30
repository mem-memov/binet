package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Graph(
  network: Network
)

object Graph:

  given [DOT, VERTEX](using
    general.network.CreateDot[Network, DOT],
    general.dot.ToVertex[DOT, VERTEX]
  ): general.graph.CreateVertex[Graph, VERTEX] with

    override
    def f(
      graph: Graph
    ): Either[String, (Graph, VERTEX)] =

      for {
        createDotResult <- graph.network.createDot()
        (network1, dot) = createDotResult
      } yield
        val vertex = dot.toVertex
        val modifiedGraph = graph.copy(network = network1)
        (modifiedGraph, vertex)

  given [ADDRESS, SOURCE, TARGET, VERTEX](using
    general.vertex.ToSource[VERTEX, Network, SOURCE],
    general.vertex.ToTarget[VERTEX, Network, TARGET],
    general.source.CreateArrowToTarget[SOURCE, Network, TARGET],
    general.source.HasTarget[SOURCE, Network, TARGET],
    general.source.IsSmallerThanTarget[SOURCE, TARGET],
    general.source.ToVertex[SOURCE, VERTEX],
    general.target.HasSource[TARGET, Network, SOURCE],
    general.target.ToVertex[TARGET, VERTEX]
  ): general.graph.ConnectVertices[Graph, VERTEX] with

    override
    def f(
      graph: Graph,
      sourceVertex: VERTEX,
      targetVertex: VERTEX
    ): Either[String, Graph] =

      for {
        source <- sourceVertex.toSource(graph.network)
        target <- targetVertex.toTarget(graph.network)
        alreadyConnected <- if source.isSmallerThanTarget(target) then
            source.hasTarget(target, graph.network)
          else
            target.hasSource(source, graph.network)
        modifiedNetwork <- if alreadyConnected then
            Right((graph.network, sourceVertex, targetVertex))
          else
            source.createArrowToTarget(target, graph.network).map {
              case (modifiedNetwork, _, _) => modifiedNetwork
            }
      } yield
        graph.copy(network = modifiedNetwork)

  given [SOURCE, TARGET, VERTEX](using
    general.vertex.ToTarget[VERTEX, Network, TARGET],
    general.target.ReadSources[TARGET, Network, SOURCE],
    general.source.ToVertex[SOURCE, VERTEX]
  ): general.graph.ReadSources[Graph, VERTEX] with

    override
    def f(
      graph: Graph,
      targetVertex: VERTEX
    ): Either[String, List[VERTEX]] =

      for {
        target <- targetVertex.toTarget(graph.network)
        sources <- target.readSources(graph.network)
      } yield sources.map(_.toVertex)

  given [SOURCE, TARGET, VERTEX](using
    general.vertex.ToSource[VERTEX, Network, SOURCE],
    general.source.ReadTargets[SOURCE, Network, TARGET],
    general.target.ToVertex[TARGET, VERTEX]
  ): general.graph.ReadTargets[Graph, VERTEX] with

    override
    def f(
      graph: Graph,
      sourceVertex: VERTEX
    ): Either[String, List[VERTEX]] =

      for {
        source <- sourceVertex.toSource(graph.network)
        targets <- source.readTargets(graph.network)
      } yield targets.map(_.toVertex)
