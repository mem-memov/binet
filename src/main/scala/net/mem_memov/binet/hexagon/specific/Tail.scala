package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

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
    general.source.InArrowTail[SOURCE]
  ): general.tail.HasSource[Tail, SOURCE] with

    override
    def f(
      tail: Tail,
      source: SOURCE
    ): Boolean =

      source.inArrowTail(tail.arrow)

  given [DOT, NETWORK, SOURCE](using
    general.arrow.GetSourceDot[Arrow, DOT, NETWORK],
    general.dot.ToSource[DOT, SOURCE]
  ): general.tail.ReadSource[Tail, NETWORK, SOURCE] with

    override
    def f(
      tail: Tail,
      network: NETWORK
    ): Either[String, SOURCE] =

      for {
        dot <- tail.arrow.getSourceDot(network)
      } yield dot.toSource

  given [NETWORK, SOURCE](using
    general.tail.ReadSource[Tail, NETWORK, SOURCE],
    general.tail.GetNext[Tail, NETWORK]
  ): general.tail.CollectSources[Tail, NETWORK, SOURCE] with

    @tailrec
    override
    final // enable tail recursive optimization
    def f(
      tail: Tail,
      network: NETWORK,
      sources: List[SOURCE]
    ): Either[String, List[SOURCE]] =

      val sourceEither = tail.readSource(network)
      sourceEither match
        case Left(error) => Left(error)
        case Right(source) =>
          val nextTailOptionEither = tail.getNext(network)
          nextTailOptionEither match
            case Left(error) => Left(error)
            case Right(nextTailOption) =>
              nextTailOption match
                case Some(nextTail) => f(nextTail, network, source :: sources)
                case None => Right(source :: sources)