package net.mem_memov.binet.hexagon.general.head

trait Follow[HEAD, NETWORK]:

  def f(
    head: HEAD,
    previousHead: HEAD,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (head: HEAD)

    def follow(
      previousHead: HEAD,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(head, previousHead, network)
