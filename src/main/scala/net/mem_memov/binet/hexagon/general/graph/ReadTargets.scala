package net.mem_memov.binet.hexagon.general.graph

trait ReadTargets[GRAPH, VERTEX]:

  def f(
    graph: GRAPH,
    vertex: VERTEX
  ): Either[String, List[VERTEX]]

  extension (graph: GRAPH)

    def readTargets(
      vertex: VERTEX
    ): Either[String, List[VERTEX]] =

      f(graph, vertex)
