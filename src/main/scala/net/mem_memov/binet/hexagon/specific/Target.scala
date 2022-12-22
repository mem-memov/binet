package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Target(
  dot: Dot
)

object Target:

  given [ADDRESS, DOT, ENTRY](using
    general.dot.GetAddress[Dot, ADDRESS]
  ): general.target.GetAddress[Target, ADDRESS] with

    override
    def f(
      target: Target
    ): ADDRESS =

      target.dot.getAddress

  given [ADDRESS, ARROW, ARROW_ENTRY, ENTRY, NETWORK](using
    general.network.CreateArrow[NETWORK, ARROW, ENTRY],
    general.dot.SetSourceArrow[Dot, ARROW, NETWORK],
    general.dot.IncrementSourceCount[Dot, NETWORK],
    general.dot.GetSourceArrow[Dot, ARROW, NETWORK],
    general.arrow.SetNextSourceArrow[ARROW, NETWORK],
    target.general.arrowEntry.Update[ARROW_ENTRY, Dot, ENTRY]
  )(using
    arrowEntry: ARROW_ENTRY
  ): general.target.CreateArrowFromSource[Target, ARROW, ENTRY, NETWORK] with

    override 
    def f(
      target: Target,
      entry: ENTRY,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      val entry2 = arrowEntry.update(entry, target.dot)

      for {
        previousArrowOption <- target.dot.getSourceArrow(network)
        createArrowResult <- network.createArrow(entry2)
        (network1, arrow) = createArrowResult
        network2 <- target.dot.setSourceArrow(arrow, network1)
        network3 <- target.dot.incrementSourceCount(network2)
        network4 <- previousArrowOption match
          case Some(previousArrow) => previousArrow.setNextSourceArrow(arrow, network3)
          case None => Right(network3)
      } yield (network4, arrow)

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

  given [ADDRESS](using
    general.dot.GetSourceCount[Dot, ADDRESS]
  ): general.target.CountSources[Target, ADDRESS] with

    override
    def f(
      target: Target
    ): ADDRESS =

      target.dot.getSourceCount

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
