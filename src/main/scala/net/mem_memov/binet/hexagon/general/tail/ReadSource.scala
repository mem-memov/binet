package net.mem_memov.binet.hexagon.general.tail

trait ReadSource[TAIL, NETWORK, SOURCE]:

  def f(
    tail: TAIL,
    network: NETWORK
  ): Either[String, SOURCE]

  extension (tail: TAIL)

    def readSource(
      network: NETWORK
    ): Either[String, SOURCE] =

      f(tail, network)

