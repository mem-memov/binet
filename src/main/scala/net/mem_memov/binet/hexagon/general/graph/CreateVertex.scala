package net.mem_memov.binet.hexagon.general.graph

trait CreateVertex[GRAPH, VERTEX]:

  def f(
    graph: GRAPH
  ): Either[String, (GRAPH, VERTEX)]

  extension (graph: GRAPH)

    def createVertex: Either[String, (GRAPH, VERTEX)] =

      f(graph)
