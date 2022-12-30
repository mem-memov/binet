package net.mem_memov.binet.hexagon.general.arrow

trait SetNextTargetArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    nextTargetArrow: ARROW,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW)]

  extension (arrow: ARROW)

    def setNextTargetArrow(
      nextTargetArrow: ARROW,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      f(arrow, nextTargetArrow, network)
