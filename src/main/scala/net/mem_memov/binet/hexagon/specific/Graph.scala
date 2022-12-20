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

  given [ADDRESS, SOURCE, TARGET, VERTEX](using
    general.vertex.ToSource[VERTEX, SOURCE],
    general.vertex.ToTarget[VERTEX, TARGET],
    general.source.CreateArrowToTarget[SOURCE, Network, TARGET],
    general.source.HasTarget[SOURCE, Network, TARGET],
    general.source.CountTargets[SOURCE, ADDRESS],
    general.target.CountSources[TARGET, ADDRESS],
    general.target.HasSource[TARGET, Network, SOURCE],
    Ordering[ADDRESS]
  ): general.graph.ConnectVertices[Graph, VERTEX] with

    override
    def f(
      graph: Graph,
      sourceVertex: VERTEX,
      targetVertex: VERTEX
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

  given [ADDRESS, VERTEX](using
    general.target.CountSources[TARGET, ADDRESS]
  ): general.graph.ReadSources[Graph, VERTEX] with

    override
    def f(
      graph: Graph,
      vertex: VERTEX
    ): Either[String, List[VERTEX]] =

      val target = targetVertex.toTarget
      
      for {
        firstHeadOption <- graph.network.
      }


      ???

