package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import scala.annotation.tailrec

case class Source(
  dot: Dot
)

object Source:

  given [ARROW, ARROW_DRAFT_BEGIN, ARROW_ENTRY, ADDRESS, ENTRY, NETWORK, TARGET](using
    general.target.CreateArrowFromSource[TARGET, ARROW, ARROW_DRAFT_BEGIN, NETWORK],
    general.dot.SetTargetArrow[Dot, ARROW, NETWORK],
    general.dot.GetTargetArrow[Dot, ARROW, NETWORK],
    general.dot.IncrementTargetCount[Dot, NETWORK],
    general.dot.BeginArrowDraft[Dot, ARROW_DRAFT_BEGIN],
    general.arrow.SetNextTargetArrow[ARROW, NETWORK]
  ): general.source.CreateArrowToTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, (NETWORK, Source, TARGET)] =

      for {
        previousArrowOption <- source.dot.getTargetArrow(network)
        createArrowResult <- target.createArrowFromSource(source.dot.beginArrowDraft, network)
        (network1, target1, arrow) = createArrowResult
        setTargetArrowResult <- source.dot.setTargetArrow(arrow, network1)
        (network2, dot2) = setTargetArrowResult
        incrementTargetCountResult <- source.dot.incrementTargetCount(network2)
        (network3, dot3) = incrementTargetCountResult
        setNextTargetArrowResult <- previousArrowOption match
          case Some(previousArrow) => previousArrow.setNextTargetArrow(arrow, network3)
          case None => Right(network3, arrow)
        (network4, _) = setNextTargetArrowResult
      } yield
        (network4, source.copy(dot = dot3), target1)

  given [ARROW, HEAD, NETWORK, TARGET](using
    general.dot.GetTargetArrow[Dot, ARROW, NETWORK],
    general.arrow.ToHead[ARROW, HEAD],
    general.target.IsInHeads[TARGET, HEAD, NETWORK]
  ): general.source.HasTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, Boolean] =

      for {
        optionArrow <- source.dot.getTargetArrow(network)
        hasTarget <- optionArrow match
          case None => Right(false)
          case Some(arrow) => target.isInHeads(arrow.toHead, network)
      } yield hasTarget

  given [NETWORK, TAIL](using
    general.tail.HasSource[TAIL, Source],
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

      if tail.hasSource(source) then
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
    general.target.HasMoreSources[TARGET, Dot]
  ): general.source.IsSmallerThanTarget[Source, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET
    ): Boolean =

      target.hasMoreSources(source.dot)
      
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
    ): Either[String, (NETWORK, Source)] =

      for {
        targetArrowOption <- source.dot.getTargetArrow(network)
        arrowToTargetOption <- targetArrowOption match
          case Some(targetArrow) => targetArrow.toHead.findArrowToTarget(target, network)
          case None => Right(None)
        result <- arrowToTargetOption match {
          case Some(arrowToTarget) => arrowToTarget.delete(network)
          case None => Right(())
        }
      } yield ???