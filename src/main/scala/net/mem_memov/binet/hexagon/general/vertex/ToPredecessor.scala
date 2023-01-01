package net.mem_memov.binet.hexagon.general.vertex

trait ToPredecessor[VERTEX, NETWORK, PREDECESSOR]:

  def f(
    vertex: VERTEX,
    network: NETWORK
  ): Either[String, PREDECESSOR]

  extension (vertex: VERTEX)

    def toPredecessor(
      network: NETWORK
    ): Either[String, PREDECESSOR] =

      f(vertex, network)
