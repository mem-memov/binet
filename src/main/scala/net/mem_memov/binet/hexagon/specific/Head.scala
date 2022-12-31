package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

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

  given [TARGET](using
    general.target.InArrowHead[TARGET, Arrow]
  ): general.head.HasTarget[Head, TARGET] with

    override
    def f(
      head: Head,
      target: TARGET
    ): Boolean =

      target.inArrowHead(head.arrow)

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


