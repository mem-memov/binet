package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Head(
  arrow: Arrow
)

object Head:

  given [NETWORK](using
    general.arrow.GetNextTargetArrow[Arrow, NETWORK],
    general.arrow.ToHead[Arrow, Head]
  ): general.head.GetNext[Head, NETWORK] with

    override
    def f(
      head: Head,
      network: NETWORK
    ): Either[String, Option[Head]] =

      for {
        optionArrow <- head.arrow.getNextTargetArrow(network)
      } yield optionArrow.map(_.toHead)

  given [ADDRESS, DOT](using
      general.dot.GetAddress[DOT, ADDRESS],
      general.arrow.HasTargetDot[Arrow, ADDRESS]
    ): general.head.HasTargetDot[Head, DOT] with

    override
    def f(
      head: Head,
      dot: DOT
    ): Boolean =

      head.arrow.hasTargetDot(dot.getAddress)


