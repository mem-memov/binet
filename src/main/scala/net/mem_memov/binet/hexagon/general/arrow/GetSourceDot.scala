package net.mem_memov.binet.hexagon.general.arrow

trait GetSourceDot[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (arrow: ARROW)

    def getSourceDot(
      network: NETWORK
    ): Either[String, NETWORK] =

      f(arrow, network)
