package net.mem_memov.binet.hexagon.general.head

trait GetNext[HEAD, NETWORK]:

  def f(
    head: HEAD,
    network: NETWORK
  ): Either[String, Option[HEAD]]

  extension (head: HEAD)

    def getNext(
      network: NETWORK
    ): Either[String, Option[HEAD]] =

      f(head, network)
