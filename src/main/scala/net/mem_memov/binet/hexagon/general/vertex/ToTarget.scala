package net.mem_memov.binet.hexagon.general.vertex

trait ToTarget[VERTEX, NETWORK, TARGET]:

  def f(
    vertex: VERTEX,
    network: NETWORK
  ): Either[String, TARGET]

  extension (vertex: VERTEX)

    def toTarget(
      network: NETWORK
    ): Either[String, TARGET] =

      f(vertex, network)
