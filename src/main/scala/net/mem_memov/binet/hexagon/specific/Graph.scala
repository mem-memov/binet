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
        (networkWithDot, dot) = createDotResult
      } yield
        val vertex = factory.makeVertex(dot)
        val modifiedGraph = Graph(networkWithDot)
        (modifiedGraph, vertex)

  given [SOURCE, TARGET](using
    general.vertex.ToSource[Vertex, SOURCE],
    general.vertex.ToTarget[Vertex, TARGET],
    general.source.CreateArrow[SOURCE, Network, TARGET],
    general.source.HasTarget[SOURCE, Network, TARGET]
  ): general.graph.ConnectVertices[Graph, Vertex] with

    override
    def f(
      graph: Graph,
      sourceVertex: Vertex,
      targetVertex: Vertex
    ): Either[String, Graph] =

      val source = sourceVertex.toSource
      val target = targetVertex.toTarget

      for {
        hasTarget <- source.hasTarget(target, graph.network) // TODO: optimize using count
        modifiedNetwork <- if hasTarget then
          Right(graph.network)
        else
          source.createArrow(target, graph.network)
      } yield Graph(modifiedNetwork)