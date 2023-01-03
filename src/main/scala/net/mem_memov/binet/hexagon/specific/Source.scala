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

  given [ADDRESS, ARROW, ARROW_DRAFT_BEGIN, ARROW_ENTRY, ENTRY, NETWORK, TARGET](using
    general.target.CreateArrowFromSource[TARGET, ARROW, ARROW_DRAFT_BEGIN, NETWORK],
    general.dot.SetTargetArrow[Dot, ARROW, NETWORK],
    general.dot.IncrementTargetCount[Dot, NETWORK],
    general.dot.BeginArrowDraft[Dot, ARROW_DRAFT_BEGIN],
    general.arrow.SetNextTargetArrow[ARROW, NETWORK],
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference],
    general.dotReference.GetAddress[DotReference, ADDRESS],
    general.arrowReference.GetAddressOption[ArrowReference, ADDRESS]
  ): general.source.CreateArrowToTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        previousArrowOption <- network.readArrow(source.sourceArrowReference)
        createArrowResult <- target.createArrowFromSource(source.dotReference.getAddress, source.sourceArrowReference.getAddressOption, network)
        (network1, arrow) = createArrowResult
        setTargetArrowResult <- source.dot.setTargetArrow(arrow, network1)
        (network2, dot2) = setTargetArrowResult
        incrementTargetCountResult <- source.dot.incrementTargetCount(network2)
        (network3, dot3) = incrementTargetCountResult
        setNextTargetArrowResult <- previousArrowOption match
          case Some(previousArrow) => previousArrow.setNextTargetArrow(arrow, network3)
          case None => Right(network3, arrow)
        (network4, _) = setNextTargetArrowResult
      } yield
        network4

  given [ARROW, HEAD, NETWORK, TARGET](using
    general.arrow.ToHead[ARROW, HEAD],
    general.target.IsInHeads[TARGET, HEAD, NETWORK],
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference]
  ): general.source.HasTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, Boolean] =

      for {
        optionArrow <- network.readArrow(source.arrowReference)
        hasTarget <- optionArrow match
          case None => Right(false)
          case Some(arrow) => target.isInHeads(arrow.toHead, network)
      } yield hasTarget

  given [NETWORK, TAIL](using
    general.tail.ReferencesDot[TAIL, Source],
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
  ): general.source.ReadTargets[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      network: NETWORK
    ): Either[String, List[TARGET]] =

      for {
        targetArrowOption <- source.dot.getTargetArrow(network)
        targets <- targetArrowOption match
          case Some(targetArrow) => targetArrow.toHead.collectTargets(network, List.empty[TARGET])
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
      
  given [ARROW](using
    general.arrow.HasSourceDot[ARROW, Dot]
  ): general.source.InArrowTail[Source, ARROW] with

    override 
    def f(
      source: Source,
      arrow: ARROW
    ): Boolean =

      arrow.hasSourceDot(source.dot)

  given [ARROW, HEAD, NETWORK, TARGET](using
    general.dot.GetTargetArrow[Dot, ARROW, NETWORK],
    general.arrow.ToHead[ARROW, HEAD],
    general.arrow.Delete[ARROW, NETWORK],
    general.head.FindArrowToTarget[HEAD, ARROW, NETWORK, TARGET]
  ): general.source.DeleteArrowToTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        targetArrowOption <- source.dot.getTargetArrow(network)
        arrowToTargetOption <- targetArrowOption match
          case Some(targetArrow) => targetArrow.toHead.findArrowToTarget(target, network)
          case None => Right(None)
        modifiedNetwork <- arrowToTargetOption match {
          case Some(arrowToTarget) => arrowToTarget.delete(network)
          case None => Right(network)
        }
      } yield modifiedNetwork