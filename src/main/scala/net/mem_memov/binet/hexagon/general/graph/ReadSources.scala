package net.mem_memov.binet.hexagon.general.graph

trait ReadSources[GRAPH, VERTEX]:

  def f(
    graph: GRAPH,
    vertex: VERTEX
  ): Either[String, List[VERTEX]]

  extension (graph: GRAPH)

    def readSources(
      vertex: VERTEX
    ): Either[String, List[VERTEX]] =

      f(graph, vertex)
