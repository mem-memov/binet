package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Target(
  dot: Dot
)

object Target:

  given [NETWORK, ADDRESS](using
    general.network.CreateArrow[NETWORK, ADDRESS],
    general.dot.GetAddress[Dot, ADDRESS]
  ): general.target.CreateArrow[Target, NETWORK, ADDRESS] with

    override 
    def f(
      target: Target, 
      sourceAddress: ADDRESS, 
      network: NETWORK
    ): Either[String, NETWORK] =

      for {
        networkWithArrow <- network.createArrow(sourceAddress, target.dot.getAddress)
      } yield networkWithArrow
