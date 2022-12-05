package net.mem_memov.binet.hexagon.general.arrow

trait SetNextTargetArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    nextTargetArrow: ARROW,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (arrow: ARROW)

    def setNextSourceArrow(
      nextTargetArrow: ARROW,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(arrow, nextTargetArrow, network)
