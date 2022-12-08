package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Source(
  dot: Dot
)

object Source:

  given [ARROW, ADDRESS, ENTRY, NETWORK, TARGET](using
    general.target.CreateArrow[TARGET, NETWORK, ADDRESS],
    general.dot.GetAddress[Dot, ADDRESS],
    general.dot.SetTargetArrow[Dot, ARROW, NETWORK],
    general.network.RequireArrow[NETWORK, ARROW]
  ): general.source.CreateArrow[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        networkWithArrow <- target.createArrow(source.dot.getAddress, network)
        arrow <- networkWithArrow.requireArrow()
        networkWithSource <- source.dot.setTargetArrow(arrow, network)
      } yield networkWithSource
