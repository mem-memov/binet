package net.mem_memov.binet.hexagon.general.tail

trait Delete[TAIL, NETWORK]:

  def f(
    tail: TAIL,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (tail: TAIL)

    def delete(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(tail, network)
