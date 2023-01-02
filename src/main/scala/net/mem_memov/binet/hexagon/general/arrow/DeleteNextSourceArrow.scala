package net.mem_memov.binet.hexagon.general.arrow

trait DeleteNextSourceArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW)]

  extension (arrow: ARROW)

    def deleteNextSourceArrow(
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      f(arrow, network)
