package net.mem_memov.binet.hexagon.general.graph

trait ReadCluster[GRAPH, VERTEX]:

  def f (
    graph: GRAPH,
    startVertex: VERTEX
  ): Either[String, Vector[VERTEX]]

  extension (graph: GRAPH)

    def readCluster(
      startVertex: VERTEX
    ): Either[String, Vector[VERTEX]] =

      f(graph, startVertex)
