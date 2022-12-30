package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Target(
  dot: Dot
)

object Target:

  given [ADDRESS, ARROW, ARROW_DRAFT_BEGIN, ARROW_DRAFT_END, ARROW_ENTRY, ENTRY, NETWORK](using
    general.network.CreateArrow[NETWORK, ARROW, ARROW_DRAFT_END],
    general.dot.SetSourceArrow[Dot, ARROW, NETWORK],
    general.dot.IncrementSourceCount[Dot, NETWORK],
    general.dot.EndArrowDraft[Dot, ARROW_DRAFT_BEGIN, ARROW_DRAFT_END],
    general.dot.GetSourceArrow[Dot, ARROW, NETWORK],
    general.arrow.SetNextSourceArrow[ARROW, NETWORK]
  )(using
    arrowEntry: ARROW_ENTRY
  ): general.target.CreateArrowFromSource[Target, ARROW, ARROW_DRAFT_BEGIN, NETWORK] with

    override 
    def f(
      target: Target,
      arrowDraftBegin: ARROW_DRAFT_BEGIN,
      network: NETWORK
    ): Either[String, (NETWORK, Target, ARROW)] =

      val arrowDraftEnd = target.dot.endArrowDraft(arrowDraftBegin)

      for {
        previousArrowOption <- target.dot.getSourceArrow(network)
        createArrowResult <- network.createArrow(arrowDraftEnd)
        (network1, arrow) = createArrowResult
        setSourceArrowResult <- target.dot.setSourceArrow(arrow, network1)
        (network2, dot2) = setSourceArrowResult
        incrementSourceCountResult <- dot2.incrementSourceCount(network2)
        (network3, dot3) = incrementSourceCountResult
        setNextSourceArrowResult <- previousArrowOption match
          case Some(previousArrow) => previousArrow.setNextSourceArrow(arrow, network3)
          case None => Right(network3, arrow)
        (network4, _) = setNextSourceArrowResult
      } yield (network4, target.copy(dot = dot3), arrow)

  given [ARROW, NETWORK, SOURCE, TAIL](using
    general.dot.GetSourceArrow[Dot, ARROW, NETWORK],
    general.source.IsInTails[SOURCE, NETWORK, TAIL],
    general.arrow.ToTail[ARROW, TAIL]
  ): general.target.HasSource[Target, NETWORK, SOURCE] with

    override
    def f(
      target: Target,
      source: SOURCE,
      network: NETWORK
    ): Either[String, Boolean] =

      for {
        optionArrow <- target.dot.getSourceArrow(network)
        hasTarget <- optionArrow match
          case None => Right(false)
          case Some(arrow) => source.isInTails(arrow.toTail, network)
      } yield hasTarget

  given [HEAD, NETWORK](using
    general.head.HasTarget[HEAD, Target],
    general.head.GetNext[HEAD, NETWORK],
  ): general.target.IsInHeads[Target, HEAD, NETWORK] with

    @tailrec
    override
    final // enable tail recursive optimization
    def f(
      target: Target,
      head: HEAD,
      network: NETWORK
    ): Either[String, Boolean] =

      if head.hasTarget(target) then
        Right(true)
      else
        val eitherOptionHead = head.getNext(network)
        eitherOptionHead match
          case Left(error) => Left(error)
          case Right(optionHead) =>
            optionHead match
              case None => Right(false)
              case Some(head) => f(target, head, network)

  given [ARROW, NETWORK, SOURCE, TAIL](using
    general.dot.GetSourceArrow[Dot, ARROW, NETWORK],
    general.arrow.ToTail[ARROW, TAIL],
    general.tail.CollectSources[TAIL, NETWORK, SOURCE],
  ): general.target.ReadSources[Target, NETWORK, SOURCE] with

    override
    def f(
      target: Target,
      network: NETWORK
    ): Either[String, List[SOURCE]] =

      for {
        sourceArrowOption <- target.dot.getSourceArrow(network)
        sources <- sourceArrowOption match
          case Some(sourceArrow) => sourceArrow.toTail.collectSources(network, List.empty[SOURCE])
          case None => Right(List.empty[SOURCE])
      } yield sources

  given [VERTEX](using
    general.dot.ToVertex[Dot, VERTEX]
  ): general.target.ToVertex[Target, VERTEX] with

    override
    def f(
      target: Target
    ): VERTEX =

      target.dot.toVertex

  given (using
    general.dot.HasMoreSourcesThanTheOtherTargets[Dot]
  ): general.target.HasMoreSources[Target, Dot] with

    override def f(
      target: Target,
      sourceDot: Dot
    ): Boolean =

      target.dot.hasMoreSourcesThanTheOtherTargets(sourceDot)