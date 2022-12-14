package net.mem_memov.binet.hexagon.general.tail

trait GetNext[TAIL, NETWORK]:

  def f(
    tail: TAIL,
    network: NETWORK
  ): Either[String, Option[TAIL]]

  extension (tail: TAIL)

    def getNext(
      network: NETWORK
    ): Either[String, Option[TAIL]] =

      f(tail, network)
