package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Graph(
  network: Network
)

object Graph:

  given [DOT, FACTORY, VERTEX](using
    general.network.CreateDot[Network, DOT],
    general.factory.MakeVertex[FACTORY, DOT, VERTEX]
  )(using
    factory: FACTORY
  ): general.graph.CreateVertex[Graph, VERTEX] with

    override
    def f(
      graph: Graph
    ): Either[String, (Graph, VERTEX)] =

      for {
        createDotResult <- graph.network.createDot()
        (network1, dot) = createDotResult
      } yield
        val vertex = factory.makeVertex(dot)
        val modifiedGraph = graph.copy(network = network1)
        (modifiedGraph, vertex)

  given [ADDRESS, SOURCE, TARGET](using
    general.vertex.ToSource[Vertex, SOURCE],
    general.vertex.ToTarget[Vertex, TARGET],
    general.source.CreateArrowToTarget[SOURCE, Network, TARGET],
    general.source.HasTarget[SOURCE, Network, TARGET],
    general.source.CountTargets[SOURCE, ADDRESS],
    general.target.CountSources[TARGET, ADDRESS],
    general.target.HasSource[TARGET, Network, SOURCE],
    Ordering[ADDRESS]
  ): general.graph.ConnectVertices[Graph, Vertex] with

    override
    def f(
      graph: Graph,
      sourceVertex: Vertex,
      targetVertex: Vertex
    ): Either[String, Graph] =

      val source = sourceVertex.toSource
      val target = targetVertex.toTarget

      import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

      for {
        alreadyConnected <- if source.countTargets < target.countSources then
            source.hasTarget(target, graph.network)
          else
            target.hasSource(source, graph.network)
        network1 <- if alreadyConnected then
            Right(graph.network)
          else
            source.createArrowToTarget(target, graph.network)
      } yield graph.copy(network = network1)