package net.mem_memov.binet.hexagon.general.arrow

trait SetPreviousTargetArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    previousTargetArrow: ARROW,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (arrow: ARROW)

    def setPreviousTargetArrow(
      previousTargetArrow: ARROW,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(arrow, previousTargetArrow, network)

