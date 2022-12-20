package net.mem_memov.binet.hexagon.general.graph

trait HasTarget[GRAPH, VERTEX]:

  def f(
    graph: GRAPH,
    vertex: VERTEX
  ): Either[String, Boolean]

  extension (graph: GRAPH)

    def hasTarget(
      vertex: VERTEX
    ): Either[String, Boolean] =

      f(graph, vertex)
