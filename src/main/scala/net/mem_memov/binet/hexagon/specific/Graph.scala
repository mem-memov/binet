package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Graph(
  optionVertex: Option[Vertex],
  network: Network
)

object Graph:

  given [DOT, FACTORY](using
    general.network.CreateDot[Network],
    general.network.GetDot[Network, DOT],
    general.factory.MakeVertex[FACTORY, DOT, Vertex]
  )(using
    factory: FACTORY
  ): general.graph.CreateVertex[Graph] with

    override
    def f(
      graph: Graph
    ): Either[String, Graph] =

      for {
        modifiedNetwork <- graph.network.createDot()
        dot <- modifiedNetwork.getDot()
      } yield
        val vertex = factory.makeVertex(dot)
        Graph(Some(vertex), modifiedNetwork)

  given [SOURCE, TARGET](using
    general.vertex.ToSource[Vertex, SOURCE],
    general.vertex.ToTarget[Vertex, TARGET],
    general.source.CreateArrow[SOURCE, Network, TARGET]
  ): general.graph.ConnectVertices[Graph, Vertex] with

    override
    def f(
      graph: Graph,
      sourceVertex: Vertex,
      targetVertex: Vertex
    ): Either[String, Graph] =

      val source = sourceVertex.toSource
      val target = targetVertex.toTarget

      // TODO: check if already connected

      for {
        networkWithArrow <- source.createArrow(target, graph.network)
      } yield Graph(None, networkWithArrow)