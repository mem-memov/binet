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
    general.target.CreateArrow[TARGET, ADDRESS, ARROW, NETWORK],
    general.dot.GetAddress[Dot, ADDRESS],
    general.dot.SetTargetArrow[Dot, ARROW, NETWORK]
  ): general.source.CreateArrow[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        createArrowResult <- target.createArrow(source.dot.getAddress, network)
        (networkWithArrow, arrow) = createArrowResult
        networkWithSource <- source.dot.setTargetArrow(arrow, network)
      } yield networkWithSource

  given [ARROW, HEAD, NETWORK, TARGET](using
    general.dot.GetTargetArrow[Dot, ARROW, NETWORK],
    general.arrow.ToHead[ARROW, HEAD],
    general.head.HasTarget[HEAD, TARGET],
    general.head.GetNext[HEAD, NETWORK]
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
          case Some(arrow) =>

            @tailrec
            def ff(head: HEAD, target: TARGET, network: NETWORK): Either[String, Boolean] =
              if head.hasTarget(target) then
                Right(true)
              else
                val eitherOptionHead = head.getNext(network)
                eitherOptionHead match
                  case Left(error) => Left(error)
                  case Right(optionHead) =>
                    optionHead match
                      case None => Right(false)
                      case Some(head) => ff(head, target, network)

            ff(arrow.toHead, target, network)

      } yield hasTarget

