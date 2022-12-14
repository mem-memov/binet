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

  given [ARROW, ADDRESS, ENTRY, NETWORK, TARGET](using
    general.target.CreateArrowFromSource[TARGET, ADDRESS, ARROW, NETWORK],
    general.dot.GetAddress[Dot, ADDRESS],
    general.dot.SetTargetArrow[Dot, ARROW, NETWORK]
  ): general.source.CreateArrowToTarget[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        createArrowResult <- target.createArrowFromSource(source.dot.getAddress, network)
        (networkWithArrow, arrow) = createArrowResult
        networkWithSource <- source.dot.setTargetArrow(arrow, network)
      } yield networkWithSource

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

