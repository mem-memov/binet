package net.mem_memov.binet.hexagon.general.graph

trait HasSource[GRAPH, VERTEX]:

  def f(
    graph: GRAPH,
    vertex: VERTEX
  ): Either[String, Boolean]

  extension (graph: GRAPH)

    def hasSource(
      vertex: VERTEX
    ): Either[String, Boolean] =

      f(graph, vertex)
