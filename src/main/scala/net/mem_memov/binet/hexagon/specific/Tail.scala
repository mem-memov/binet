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

  given [ADDRESS, DOT](using
    general.dot.GetAddress[DOT, ADDRESS],
    general.arrow.HasSourceDot[Arrow, ADDRESS]
  ): general.tail.HasSourceDot[Tail, DOT] with

    override
    def f(
      tail: Tail,
      dot: DOT
    ): Boolean =

      tail.arrow.hasSourceDot(dot.getAddress)