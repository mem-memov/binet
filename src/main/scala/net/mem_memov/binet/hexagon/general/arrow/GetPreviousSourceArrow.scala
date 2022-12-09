package net.mem_memov.binet.hexagon.general.arrow

trait GetPreviousSourceArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, Option[ARROW]]

  extension (arrow: ARROW)

    def getPreviousSourceArrow(
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      f(arrow, network)
