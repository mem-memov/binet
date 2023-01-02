package net.mem_memov.binet.hexagon.general.arrow

trait DeleteNextTargetArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW)]

  extension (arrow: ARROW)

    def deleteNextTargetArrow(
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      f(arrow, network)
