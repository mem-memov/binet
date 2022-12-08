package net.mem_memov.binet.hexagon.general.tail

trait GetNext[TAIL, NETWORK]:

  def f(
    head: TAIL,
    network: NETWORK
  ): Either[String, Option[TAIL]]

  extension (head: TAIL)

    def getNext(
      network: NETWORK
    ): Either[String, Option[TAIL]] =

      f(head, network)
