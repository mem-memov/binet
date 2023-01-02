package net.mem_memov.binet.hexagon.general.tail

trait FindArrow[TAIL, ARROW, NETWORK, SOURCE]:

  def f(
    tail: TAIL,
    source: SOURCE,
    network: NETWORK
  ): Either[String, Option[ARROW]]

  extension (tail: TAIL)

    def findSource(
      source: SOURCE,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      f(tail, source, network)
