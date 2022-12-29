package net.mem_memov.binet.hexagon.general.vertex

trait ToSource[VERTEX, NETWORK, SOURCE]:

  def f(
    vertex: VERTEX,
    network: NETWORK
  ): Either[String, SOURCE]

  extension (vertex: VERTEX)

    def toSource(
      network: NETWORK
    ): Either[String, SOURCE] =

      f(vertex, network)
