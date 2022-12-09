package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Target(
  dot: Dot
)

object Target:

  given [ARROW, NETWORK, ADDRESS](using
    general.network.CreateArrow[NETWORK, ADDRESS, ARROW],
    general.dot.GetAddress[Dot, ADDRESS],
    general.dot.SetSourceArrow[Dot, ARROW, NETWORK]
  ): general.target.CreateArrow[Target, NETWORK, ADDRESS] with

    override 
    def f(
      target: Target, 
      sourceAddress: ADDRESS, 
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      for {
        createArrowResult <- network.createArrow(sourceAddress, target.dot.getAddress)
        (networkWithArrow, arrow) = createArrowResult
        networkWithTarget <- target.dot.setSourceArrow(arrow, network)
      } yield (networkWithTarget, arrow)
