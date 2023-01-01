package net.mem_memov.binet.hexagon.general.graph

trait SetNext[GRAPH, VERTEX]:

  def f(
    graph: GRAPH,
    predecessorVertex: VERTEX,
    successorVertex: VERTEX
  ): Either[String, GRAPH]

  extension (graph: GRAPH)

    def setNext(
      predecessorVertex: VERTEX,
      successorVertex: VERTEX
    ): Either[String, GRAPH] =

      f(graph, predecessorVertex, successorVertex)
