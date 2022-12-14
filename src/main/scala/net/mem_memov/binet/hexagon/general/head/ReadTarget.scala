package net.mem_memov.binet.hexagon.general.head

trait ReadTarget[HEAD, NETWORK, TARGET]:

  def f(
    head: HEAD,
    network: NETWORK
  ): Either[String, TARGET]

  extension (head: HEAD)

    def readTarget(
      network: NETWORK
    ): Either[String, TARGET] =

      f(head, network)
