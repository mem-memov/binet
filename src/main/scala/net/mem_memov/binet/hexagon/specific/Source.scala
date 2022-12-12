package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Source(
  dot: Dot
)

object Source:

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
