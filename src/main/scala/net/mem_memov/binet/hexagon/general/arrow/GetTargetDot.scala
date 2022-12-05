package net.mem_memov.binet.hexagon.general.arrow

trait GetTargetDot[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (arrow: ARROW)

    def getTargetDot(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(arrow, network)
