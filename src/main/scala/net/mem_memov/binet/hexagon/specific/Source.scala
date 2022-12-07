package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Source(
  dot: Dot
)

object Source:

  given [ADDRESS, NETWORK, TARGET](using
    general.target.CreateArrow[TARGET, NETWORK, ADDRESS],
    general.dot.GetAddress[Dot, ADDRESS],
  ): general.source.CreateArrow[Source, NETWORK, TARGET] with

    override
    def f(
      source: Source,
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        networkWithArrow <- target.createArrow(source.dot.getAddress, network)
      } yield networkWithArrow
