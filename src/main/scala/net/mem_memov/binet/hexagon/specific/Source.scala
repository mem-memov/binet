package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import scala.annotation.tailrec

case class Source(
  dot: Dot
)

object Source:

  given [ADDRESS](using
    general.dot.GetAddress[Dot, ADDRESS]
  ): general.source.GetAddress[Source, ADDRESS] with

    override
    def f(
      source: Source
    ): ADDRESS =

      source.dot.getAddress

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
    ): Either[String, NETWORK] =

      for {
        previousArrowOption <- source.dot.getTargetArrow(network)
        createArrowResult <- target.createArrowFromSource(source.dot.beginArrowDraft, network)
        (network1, arrow) = createArrowResult
        network2 <- source.dot.setTargetArrow(arrow, network1)
        incrementTargetCountResult <- source.dot.incrementTargetCount(network2)
        (network3, dot1) = incrementTargetCountResult
        network4 <- previousArrowOption match
          case Some(previousArrow) => previousArrow.setNextTargetArrow(arrow, network)
          case None => Right(network3)
      } yield 
        network4

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

  given [ADDRESS](using
    general.dot.GetTargetCount[Dot, ADDRESS]
  ): general.source.CountTargets[Source, ADDRESS] with

    override
    def f(
      source: Source
    ): ADDRESS =

      source.dot.getTargetCount

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