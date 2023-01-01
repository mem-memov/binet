package net.mem_memov.binet.hexagon.general.vertex

trait ToPredecessor[VERTEX, NETWORK, PREDECSSOR]:

  def f(
    vertex: VERTEX,
    network: NETWORK
  ): Either[String, PREDECSSOR]

  extension (vertex: VERTEX)

    def toPredecessor(
      network: NETWORK
    ): Either[String, PREDECSSOR] =

      f(vertex, network)
