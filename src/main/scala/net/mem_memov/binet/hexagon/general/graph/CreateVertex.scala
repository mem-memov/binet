package net.mem_memov.binet.hexagon.general.graph

trait CreateVertex[GRAPH]:

  def f(
    graph: GRAPH
  ): Either[String, GRAPH]

  extension (graph: GRAPH)

    def createVertex: Either[String, GRAPH] =

      f(graph)
