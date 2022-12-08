package net.mem_memov.binet.hexagon.general.arrow

trait GetNextTargetArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, ARROW]

  extension (arrow: ARROW)

    def getNextTargetArrow(
      network: NETWORK
    ): Either[String, ARROW] =

      f(arrow, network)
