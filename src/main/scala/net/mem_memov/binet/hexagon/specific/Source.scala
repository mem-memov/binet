package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import scala.annotation.tailrec

case class Source(
  dotReference: DotReference,
  nextDotReference: DotReference,
  sourceCounter: Counter,
  targetCounter: Counter,
  sourceArrowReference: ArrowReference,
  targetArrowReference: ArrowReference
)

object Source:

  given [ADDRESS, ARROW, NETWORK, TARGET, TAIL](using
    general.dotReference.GetAddress[DotReference, ADDRESS],
    general.arrowReference.GetAddressOption[ArrowReference, ADDRESS],
    general.arrowReference.ReadTail[ArrowReference, NETWORK, TAIL],
    general.tail.Follow[TAIL, NETWORK],
    general.tail.GetReferencedBy[TAIL, ArrowReference, NETWORK],
    general.counter.Increment[Counter, NETWORK],
    general.target.CreateTailFromSource[TARGET, ADDRESS, NETWORK, TAIL]
  ): general.source.CreateArrowToTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {

        nextTailOption <- source.sourceArrowReference.readTail(network)

        result1 <- target.createTailFromSource(source.dotReference.getAddress, source.sourceArrowReference.getAddressOption, network)
        (network1, tail) = result1

        result2 <- tail.getReferencedBy(source.sourceArrowReference, network1)
        (network2, _) = result2

        result3 <- source.sourceCounter.increment(network)
        (network3, _) = result3

        modifiedNetwork <- nextTailOption match
          case Some(nextTail) => nextTail.follow(tail, network3)
          case None => Right(network3)

      } yield modifiedNetwork

  given [HEAD, NETWORK, TARGET](using
    general.target.IsInHeads[TARGET, HEAD, NETWORK],
    general.arrowReference.ReadHead[ArrowReference, HEAD, NETWORK]
  ): general.source.HasTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, Boolean] =

      for {
        optionHead <- source.sourceArrowReference.readHead(network)
        hasTarget <- optionHead match
          case None => Right(false)
          case Some(head) => target.isInHeads(head, network)
      } yield hasTarget

  given [NETWORK, TAIL](using
    general.tail.ReferencesDot[TAIL, DotReference],
    general.tail.GetNext[TAIL, NETWORK]
  ): general.source.IsInTails[Source, NETWORK, TAIL] with

    @tailrec
    override
    final // enable tail recursive optimization
    def f(
      source: Source,
      tail: TAIL,
      network: NETWORK
    ): Either[String, Boolean] =

      if tail.referencesDot(source.dotReference) then
        Right(true)
      else
        val eitherOptionTail = tail.getNext(network)
        eitherOptionTail match
          case Left(error) => Left(error)
          case Right(optionTail) =>
            optionTail match
              case None => Right(false)
              case Some(tail) => f(source, tail, network)

  given [ARROW, HEAD, NETWORK, TARGET](using
    general.dot.GetTargetArrow[Dot, ARROW, NETWORK],
    general.arrow.ToHead[ARROW, HEAD],
    general.head.CollectTargets[HEAD, NETWORK, TARGET],
    general.arrowReference.ReadHead[ArrowReference, NETWORK, HEAD]
  ): general.source.ReadTargets[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      network: NETWORK
    ): Either[String, List[TARGET]] =

      for {
        headOption <- source.targetArrowReference.readHead(network)
        targets <- headOption match
          case Some(head) => head.collectTargets(network, List.empty[TARGET])
          case None => Right(List.empty[TARGET])
      } yield targets

  given [VERTEX](using
    general.dot.ToVertex[Dot, VERTEX]
  ): general.source.ToVertex[Source, VERTEX] with

    override
    def f(
      source: Source
    ): VERTEX =

      source.dot.toVertex

  given [TARGET](using
    general.target.HasMoreHeads[TARGET, Counter]
  ): general.source.IsSmallerThanTarget[Source, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET
    ): Boolean =

      target.hasMoreHeads(source.sourceCounter)

  given [ARROW, HEAD, NETWORK, TARGET](using
    general.dot.GetTargetArrow[Dot, ARROW, NETWORK],
    general.arrow.ToHead[ARROW, HEAD],
    general.arrow.Delete[ARROW, NETWORK],
    general.head.FindTarget[HEAD, ARROW, NETWORK, TARGET],
    general.arrowReference.ReadHead[ArrowReference, HEAD, NETWORK]
  ): general.source.DeleteArrowToTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        firstHeadOption <- source.targetArrowReference.readHead(network)
        foundHeadOption <- firstHeadOption match
          case Some(firstHead) => firstHead.findTarget(target, network)
          case None => Right(None)
        modifiedNetwork <- foundHeadOption match {
          case Some(foundHead) => foundHead.delete(network)
          case None => Right(network)
        }
      } yield modifiedNetwork