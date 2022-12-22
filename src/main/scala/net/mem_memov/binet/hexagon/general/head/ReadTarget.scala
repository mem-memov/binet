package net.mem_memov.binet.hexagon.general.head

trait ReadTarget[HEAD, NETWORK, SOURCE]:

  def f(
    head: HEAD,
    network: NETWORK
  ): Either[String, SOURCE]

  extension (head: HEAD)

    def readTarget(
      network: NETWORK
    ): Either[String, SOURCE] =

      f(head, network)
