package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Target(
  identifierDotReference: DotReference,
  nextDotReference: DotReference,
  sourceCounter: Counter,
  sourceArrowReference: ArrowReference,
  targetCounter: Counter,
  targetArrowReference: ArrowReference
)

object Target:

  given [ADDRESS, ARROW, ARROW_ENTRY, ENTRY, HEAD, NETWORK](using
    general.arrow.SetNextSourceArrow[ARROW, NETWORK],
    general.arrow.ToHead[ARROW, HEAD],
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference],
    general.dotReference.GetAddress[DotReference, ADDRESS],
    general.arrowReference.GetAddressOption[ArrowReference, ADDRESS],
    general.counter.Increment[Counter, NETWORK]
  )(using
    arrowEntry: ARROW_ENTRY
  ): general.target.CreateArrowFromSource[Target, ADDRESS, ARROW, NETWORK] with

    override 
    def f(
      target: Target,
      sourceDotAddress: ADDRESS,
      sourceArrowAddressOption: Option[ADDRESS],
      network: NETWORK
    ): Either[String, (NETWORK, Target, ARROW)] =

      val arrowDraftEnd = target.dot.endArrowDraft(arrowDraftBegin)

      for {
        previousArrowOption <- network.readArrow(target.arrowReference)
        result1 <- network.createArrow(sourceDotAddress, sourceArrowAddressOption, target.dotReference.getAddress, target.arrowReference.getAddressOption)
        (network1, arrow) = result1
        result2 <- arrow.getReferencedBy(target.arrowReference)
        (network2, modifiedArrowReference) = result2
        result3 <- target.counter.increment(network2)
        (network3, modifiedCounter) = result3
        modifiedNetwork <- previousArrowOption match
          case Some(previousArrow) => previousArrow.toHead.follow(arrow.toHead, result4)
          case None => Right(network3, arrow)
      } yield (modifiedNetwork, arrow)

  given [ARROW, NETWORK, SOURCE, TAIL](using
    general.dot.GetSourceArrow[Dot, ARROW, NETWORK],
    general.source.IsInTails[SOURCE, NETWORK, TAIL],
    general.arrow.ToTail[ARROW, TAIL],
    general.network.ReadArrow[NETWORK, ARROW, ArrowReference]
  ): general.target.HasSource[Target, NETWORK, SOURCE] with

    override
    def f(
      target: Target,
      source: SOURCE,
      network: NETWORK
    ): Either[String, Boolean] =

      for {
        optionArrow <- network.readArrow(target.arrowReference)
        hasTarget <- optionArrow match
          case None => Right(false)
          case Some(arrow) => source.isInTails(arrow.toTail, network)
      } yield hasTarget

  given [HEAD, NETWORK](using
    general.head.ReferencesDot[HEAD, Target],
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

      if head.referencesDot(target.dotReference) then
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
    general.counter.IsLarger[Counter]
  ): general.target.HasMoreHeads[Target, Counter] with

    override def f(
      target: Target,
      counter: Counter
    ): Boolean =

      target.counter(counter)

  given [ARROW](using
    general.arrow.HasTargetDot[ARROW, Dot]
  ): general.target.InArrowHead[Target, ARROW] with

    override
    def f(
      target: Target,
      arrow: ARROW
    ): Boolean =

      arrow.hasTargetDot(target.dot)