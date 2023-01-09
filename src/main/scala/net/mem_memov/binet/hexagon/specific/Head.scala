package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Head(
  tailDotReference: DotReference,
  previousTailArrowReference: ArrowReference,
  nextTailArrowReference: ArrowReference,
  headDotReference: DotReference,
  previousHeadArrowReference: ArrowReference,
  nextHeadArrowReference: ArrowReference
)

object Head:

  given [FACTORY, TAIL](using
    general.factory.MakeTail[FACTORY, ArrowReference, DotReference, TAIL]
  )(using
    factory: FACTORY
  ): general.head.ToTail[Head, TAIL] with

    override
    def f(
      head: Head
    ): TAIL =

      factory.makeTail(
        head.tailDotReference,
        head.previousTailArrowReference,
        head.nextTailArrowReference,
        head.headDotReference,
        head.previousHeadArrowReference,
        head.nextHeadArrowReference
      )

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

  given [TARGET](using
    general.dotReference.InSameDirection[DotReference]
  ): general.head.ReferencesDot[Head, DotReference] with

    override
    def f(
      head: Head,
      dotReference: DotReference
    ): Boolean =

      head.dotReference.inSameDirection(dotReference)

  given [NETWORK, TARGET](using
    general.head.ReadTarget[Head, NETWORK, TARGET],
    general.head.GetNext[Head, NETWORK]
  ): general.head.CollectTargets[Head, NETWORK, TARGET] with

    @tailrec
    override
    final // enable tail recursive optimization
    def f(
      head: Head,
      network: NETWORK,
      targets: List[TARGET]
    ): Either[String, List[TARGET]] =

      val targetEither = head.readTarget(network)
      targetEither match
        case Left(error) => Left(error)
        case Right(target) =>
          val nextHeadOptionEither = head.getNext(network)
          nextHeadOptionEither match
            case Left(error) => Left(error)
            case Right(nextHeadOption) =>
              nextHeadOption match
                case Some(nextHead) => f(nextHead, network, target :: targets)
                case None => Right(target :: targets)

  given [DOT, NETWORK, TARGET](using
    general.dot.DeleteTargetArrow[DOT, NETWORK],
    general.dotReference.ReadTarget[DotReference, NETWORK, TARGET],
    general.arrowReference.ReadHead[ArrowReference, Head, NETWORK],
    general.arrowReference.ReferencePath[ArrowReference, DotReference, NETWORK],
    general.arrowReference.Clear[ArrowReference, NETWORK],
    general.target.ReferenceHead[TARGET, DotReference, NETWORK],
    general.target.ClearArrowReference[TARGET, NETWORK]
  ): general.head.Delete[Head, NETWORK] with

    override
    def f(
      head: Head,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        target <- head.headDotReference.readTarget(network)
        previousHeadOption <- head.previousHeadArrowReference.readHead(network)
        nextHeadOption <- head.nextHeadArrowReference.readHead(network)
        modifiedNetwork <- previousHeadOption match
          case Some(previousHead) =>
            nextHeadOption match
              case Some(nextHead) =>
                for {
                  result1 <- previousHead.nextHeadArrowReference.referencePath(nextHead.tailDotReference, network)
                  (network1, _) = result1
                  result2 <- nextHead.previousHeadArrowReference.referencePath(previousHead.tailDotReference, network1)
                  (network2, _) = result2
                } yield network2
              case None =>
                for {
                  result1 <- previousHead.nextHeadArrowReference.clear(network)
                  (network1, _) = result1
                } yield network1
          case None =>
            nextHeadOption match
              case Some(nextHead) =>
                for {
                  result1 <- target.referenceHead(nextHead.tailDotReference, network)
                  (network1, _) = result1
                  result2 <- nextHead.previousHeadArrowReference.clear(network)
                  (network2, _) = result2
                } yield network2
              case None =>
                for {
                  result1 <- target.clearArrowReference(network)
                  (network1, _) = result1
                } yield network1
      } yield modifiedNetwork

  given [NETWORK](using
    general.arrowReference.
  ): general.head.Follow[Head, NETWORK] with

    override
    def f(
      head: Head,
      previousHead: Head,
      network: NETWORK
    ): Either[String, NETWORK] =

      previousHead.nextArrowReference
      head.previousArrowReference

  given [NETWORK](using
    general.arrowReference.ReferencePath[ArrowReference, NETWORK]
  ): general.tail.GetReferencedBy[Head, ArrowReference, NETWORK] with

    override
    def f(
      head: Head,
      arrowReference: ArrowReference,
      network: NETWORK
    ): Either[String, (NETWORK, ArrowReference)] =

      arrowReference.referencePath(head.tailDotReference, network)