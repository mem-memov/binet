package net.mem_memov.binet.hexagon.general.arrow

trait SetNextSourceArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    nextSourceArrow: ARROW,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (arrow: ARROW)

    def setNextSourceArrow(
      nextSourceArrow: ARROW,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(arrow, nextSourceArrow, network)
