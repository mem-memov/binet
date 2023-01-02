package net.mem_memov.binet.hexagon.general.arrow

trait Delete[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (arrow: ARROW)

    def delete(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(arrow, network)
