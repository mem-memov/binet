package net.mem_memov.binet.hexagon.general.arrow

trait GetPreviousTargetArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, ARROW]

  extension (arrow: ARROW)

    def getPreviousTargetArrow(
      network: NETWORK
    ): Either[String, ARROW] =

      f(arrow, network)
