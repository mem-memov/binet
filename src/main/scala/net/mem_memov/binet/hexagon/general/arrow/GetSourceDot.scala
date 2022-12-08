package net.mem_memov.binet.hexagon.general.arrow

trait GetSourceDot[ARROW, DOT, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, DOT]

  extension (arrow: ARROW)

    def getSourceDot(
      network: NETWORK
    ): Either[String, DOT] =

      f(arrow, network)
