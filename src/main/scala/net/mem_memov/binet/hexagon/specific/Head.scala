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

  given [DOT, NETWORK](using
    general.network.ReadArrow[NETWORK, Arrow, ArrowReference],
    general.network.ReadDot[NETWORK, DOT, DotReference],
    general.arrow.SetNextTargetArrow[Arrow, NETWORK],
    general.arrow.SetPreviousTargetArrow[Arrow, NETWORK],
    general.arrow.DeleteNextTargetArrow[Arrow, NETWORK],
    general.arrow.DeletePreviousTargetArrow[Arrow, NETWORK],
    general.dot.SetTargetArrow[DOT, Arrow, NETWORK],
    general.dot.DeleteTargetArrow[DOT, NETWORK]
  ): general.head.Delete[Head, NETWORK] with

    override
    def f(
      head: Head,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        targetDot <- network.readDot(head.dotReference)
        previousTargetArrowOption <- network.readArrow(head.previousArrowReference)
        nextTargetArrowOption <- network.readArrow(head.nextArrowReference)
        modifiedNetwork <- previousTargetArrowOption match
          case Some(previousTargetArrow) =>
            nextTargetArrowOption match
              case Some(nextTargetArrow) =>
                for {
                  setNextTargetArrowResult <- previousTargetArrow.setNextTargetArrow(nextTargetArrow, network)
                  (network1, _) = setNextTargetArrowResult
                  setPreviousTargetArrowResult <- nextTargetArrow.setPreviousTargetArrow(previousTargetArrow, network1)
                  (network2, _) = setPreviousTargetArrowResult
                } yield network2
              case None =>
                for {
                  deleteNextTargetArrowResult <- previousTargetArrow.deleteNextTargetArrow(network)
                  (network1, _) = deleteNextTargetArrowResult
                } yield network1
          case None =>
            nextTargetArrowOption match
              case Some(nextTargetArrow) =>
                for {
                  setTargetArrowResult <- targetDot.setTargetArrow(nextTargetArrow, network)
                  (network1, _) = setTargetArrowResult
                  deletePreviousTargetArrowResult <- nextTargetArrow.deletePreviousTargetArrow(network1)
                  (network2, _) = deletePreviousTargetArrowResult
                } yield network2
              case None =>
                for {
                  deleteTargetArrowResult <- targetDot.deleteTargetArrow(network)
                  (network1, _) = deleteTargetArrowResult
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
