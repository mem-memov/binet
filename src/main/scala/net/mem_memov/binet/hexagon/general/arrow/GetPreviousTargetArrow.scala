package net.mem_memov.binet.hexagon.general.arrow

trait GetPreviousTargetArrow[ARROW, NETWORK]:

  def f(
    arrow: ARROW,
    network: NETWORK
  ): Either[String, Option[ARROW]]

  extension (arrow: ARROW)

    def getPreviousTargetArrow(
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      f(arrow, network)
