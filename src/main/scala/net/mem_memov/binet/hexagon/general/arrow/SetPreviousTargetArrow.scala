package net.mem_memov.binet.hexagon.general.arrow

trait SetPreviousTargetArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    previousTargetArrow: ARROW,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW)]

  extension (arrow: ARROW)

    def setPreviousTargetArrow(
      previousTargetArrow: ARROW,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      f(arrow, previousTargetArrow, network)

