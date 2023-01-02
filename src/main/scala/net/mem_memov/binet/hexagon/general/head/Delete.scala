package net.mem_memov.binet.hexagon.general.head

trait Delete[HEAD, NETWORK]:

  def f(
    head: HEAD,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (head: HEAD)

    def delete(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(head, network)
