package net.mem_memov.binet.hexagon.general.arrow

trait GetPreviousSourceArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, ARROW]

  extension (arrow: ARROW)

    def getPreviousSourceArrow(
      network: NETWORK
    ): Either[String, ARROW] =

      f(arrow, network)
