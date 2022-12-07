package net.mem_memov.binet.hexagon.general.graph

trait ConnectVertices[GRAPH, VERTEX]:

  def f(
    graph: GRAPH,
    sourceVertex: VERTEX,
    targetVertex: VERTEX
  ): Either[String, GRAPH]

  extension (graph: GRAPH)

    def connectVertices(
      sourceVertex: VERTEX,
      targetVertex: VERTEX
    ): Either[String, GRAPH] =

      f(graph, sourceVertex, targetVertex)
