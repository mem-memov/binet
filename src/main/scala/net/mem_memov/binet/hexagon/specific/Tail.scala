package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Tail(
  arrow: Arrow
)

object Tail:

  given [NETWORK](using
    general.arrow.GetNextSourceArrow[Arrow, NETWORK],
    general.arrow.ToTail[Arrow, Tail]
  ): general.tail.GetNext[Tail, NETWORK] with

    override
    def f(
      tail: Tail,
      network: NETWORK
    ): Either[String, Option[Tail]] =

      for {
        optionArrow <- tail.arrow.getNextSourceArrow(network)
      } yield optionArrow.map(_.toTail)

  given [ADDRESS, SOURCE](using
    general.dot.GetAddress[SOURCE, ADDRESS],
    general.arrow.HasSourceDot[Arrow, ADDRESS]
  ): general.tail.HasSource[Tail, SOURCE] with

    override
    def f(
      tail: Tail,
      source: SOURCE
    ): Boolean =

      tail.arrow.hasSourceDot(source.getAddress)