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

  given [ARROW, ADDRESS, ENTRY, FACTORY, NETWORK, TARGET](using
    general.target.CreateArrowFromSource[TARGET, ARROW, ENTRY, NETWORK],
    general.dot.GetAddress[Dot, ADDRESS],
    general.dot.SetTargetArrow[Dot, ARROW, NETWORK],
    general.dot.IncrementTargetCount[Dot, NETWORK],
    general.dot.GetTargetArrow[Dot, ARROW, NETWORK],
    general.dot.GetTargetArrowAddress[Dot, ADDRESS],
    general.arrow.SetNextTargetArrow[ARROW, NETWORK],
    general.factory.EmptyEntry[FACTORY, ENTRY],
    general.entry.SetAddress1[ENTRY, ADDRESS],
    general.entry.SetAddress2[ENTRY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.source.CreateArrowToTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      val entry1 = factory.emptyEntry().setAddress1(source.dot.getAddress)

      val entry2 = source.dot.getTargetArrowAddress match {
        case Some(targetArrowAddress) => entry1.setAddress2(targetArrowAddress)
        case None => entry1
      }

      for {

        previousArrowOption <- source.dot.getTargetArrow(network)
        createArrowResult <- target.createArrowFromSource(entry2, network)
        (network1, arrow) = createArrowResult
        network2 <- source.dot.setTargetArrow(arrow, network1)
        network3 <- source.dot.incrementTargetCount(network2)
        network4 <- previousArrowOption match
          case Some(previousArrow) => previousArrow.setNextTargetArrow(arrow, network)
          case None => Right(network3)
      } yield network4

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