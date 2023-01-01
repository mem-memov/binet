package net.mem_memov.binet.hexagon.general.vertex

trait ToSuccessor[VERTEX, NETWORK, SUCCESSOR]:

  def f(
    vertex: VERTEX,
    network: NETWORK
  ): Either[String, SUCCESSOR]

  extension (vertex: VERTEX)

    def toSuccessor(
      network: NETWORK
    ): Either[String, SUCCESSOR] =

      f(vertex, network)
