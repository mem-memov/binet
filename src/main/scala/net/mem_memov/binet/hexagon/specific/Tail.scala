package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Tail(
  tailDotReference: DotReference,
  previousTailArrowReference: ArrowReference,
  nextTailArrowReference: ArrowReference,
  headDotReference: DotReference,
  previousHeadArrowReference: ArrowReference,
  nextHeadArrowReference: ArrowReference
)

object Tail:

  given [ARROW, NETWORK](using
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference],
    general.arrow.ToTail[Arrow, Tail]
  ): general.tail.GetNext[Tail, NETWORK] with

    override
    def f(
      tail: Tail,
      network: NETWORK
    ): Either[String, Option[Tail]] =

      for {
        optionArrow <- network.readArrow(tail.nextArrowReference)
        optionTail <- optionArrow match
          case Some(arrow) => arrow.toTail
          case None => Right(None)
      } yield optionTail

  given [SOURCE](using
    general.dotReference.InSameDirection[DotReference]
  ): general.tail.ReferencesDot[Tail, DotReference] with

    override
    def f(
      tail: Tail,
      dotReference: DotReference
    ): Boolean =

      tail.dotReference.inSameDirection(dotReference)

  given [ARROW, NETWORK, SOURCE]: general.tail.FindArrow[Tail, ARROW, NETWORK, SOURCE] with

    override
    def f(
      tail: Tail,
      source: SOURCE,
      network: NETWORK
    ): Either[String, Option[ARROW]] =

      val dotEither = network.readDot(tail.dotReference)
      dotEither match
        case Left(error) => Left(error)
        case Right(dot) => ???


//      dotEither match
//        case Left(error) => Left(error)
//        case Right(dot) =>
//          val nextArrowOptionEither = network.readArrow(tail.nextArrowReference)
//          nextArrowOptionEither match
//            case Left(error) => Left(error)
//            case Right(nextArrowOption) =>
//              nextArrowOption match
//                case Some(nextArrow) => f(nextArrow.toTail, network, dot.toSource :: sources)
//                case None => Right(dot.toSource :: sources)

  given [ARROW, DOT, NETWORK, SOURCE](using
    general.network.ReadDot[NETWORK, DOT, DotReference],
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference],
    general.dot.ToSource[DOT, SOURCE],
    general.arrow.ToTail[ARROW, Tail]
  ): general.tail.CollectSources[Tail, NETWORK, SOURCE] with

    @tailrec
    override
    final // enable tail recursive optimization
    def f(
      tail: Tail,
      network: NETWORK,
      sources: List[SOURCE]
    ): Either[String, List[SOURCE]] =

      val dotEither = network.readDot(tail.dotReference)
      dotEither match
        case Left(error) => Left(error)
        case Right(dot) =>
          val nextArrowOptionEither = network.readArrow(tail.nextArrowReference)
          nextArrowOptionEither match
            case Left(error) => Left(error)
            case Right(nextArrowOption) =>
              nextArrowOption match
                case Some(nextArrow) => f(nextArrow.toTail, network, dot.toSource :: sources)
                case None => Right(dot.toSource :: sources)

  given [DOT, NETWORK, SOURCE](using
    general.dot.DeleteSourceArrow[DOT, NETWORK],
    general.dotReference.ReadSource[DotReference, NETWORK, SOURCE],
    general.arrowReference.ReadTail[ArrowReference, NETWORK, Tail],
    general.arrowReference.ReferencePath[ArrowReference, DotReference, NETWORK],
    general.arrowReference.Clear[ArrowReference, NETWORK],
    general.source.ReferenceTail[SOURCE, DotReference, NETWORK],
    general.source.ClearArrowReference[SOURCE, NETWORK]
  ): general.tail.Delete[Tail, NETWORK] with

    override
    def f(
      tail: Tail,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        source <- tail.tailDotReference.readSource(network)
        previousTailOption <- tail.previousTailArrowReference.readTail(network)
        nextTailOption <- tail.nextTailArrowReference.readTail(network)
        modifiedNetwork <- previousTailOption match
          case Some(previousTail) =>
            nextTailOption match
              case Some(nextTail) =>
                for {
                  result1 <- previousTail.nextTailArrowReference.referencePath(nextTail.tailDotReference, network)
                  (network1, _) = result1
                  result2 <- nextTail.previousTailArrowReference.referencePath(previousTail.tailDotReference, network1)
                  (network2, _) = result2
                } yield network2
              case None =>
                for {
                  result1 <- previousTail.nextTailArrowReference.clear(network)
                  (network1, _) = result1
                } yield network1
          case None =>
            nextTailOption match
              case Some(nextTail) =>
                for {
                  result1 <- source.referenceTail(nextTail.tailDotReference, network)
                  (network1, _) = result1
                  result2 <- nextTail.previousTailArrowReference.clear(network1)
                  (network2, _) = result2
                } yield network2
              case None =>
                for {
                  result1 <- source.clearArrowReference(network)
                  (network1, _) = result1
                } yield network1
      } yield modifiedNetwork

  given [NETWORK](using
    general.arrowReference.ReferencePath[ArrowReference, NETWORK]
  ): general.tail.GetReferencedBy[Tail, ArrowReference, NETWORK] with

    override
    def f(
      tail: Tail,
      arrowReference: ArrowReference,
      network: NETWORK
    ): Either[String, (NETWORK, ArrowReference)] =

      arrowReference.referencePath(tail.tailDotReference, network)

  given [NETWORK](using
    general.arrowReference.ReferencePath[ArrowReference, NETWORK]
  ): general.tail.Follow[Tail, NETWORK] with

    override
    def f(
      tail: Tail,
      previousTail: Tail,
      network: NETWORK
    ): Either[String, NETWORK] =

      previousTail.nextTailArrowReference.referencePath(tail.dotReference, network))