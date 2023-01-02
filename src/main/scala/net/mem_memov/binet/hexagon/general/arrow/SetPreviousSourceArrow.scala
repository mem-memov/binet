package net.mem_memov.binet.hexagon.general.arrow

trait SetPreviousSourceArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    previousSourceArrow: ARROW,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW)]

  extension (arrow: ARROW)

    def setPreviousSourceArrow(
      previousSourceArrow: ARROW,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      f(arrow, previousSourceArrow, network)
