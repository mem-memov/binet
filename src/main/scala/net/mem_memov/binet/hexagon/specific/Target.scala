package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

import scala.annotation.tailrec

case class Target(
  dot: Dot
)

object Target:

  given [ADDRESS](using
    general.dot.GetAddress[Dot, ADDRESS]
  ): general.target.GetAddress[Target, ADDRESS] with

    override
    def f(
      target: Target
    ): ADDRESS =

      target.dot.getAddress

  given [ARROW, NETWORK, ADDRESS](using
    general.network.CreateArrow[NETWORK, ADDRESS, ARROW],
    general.dot.GetAddress[Dot, ADDRESS],
    general.dot.SetSourceArrow[Dot, ARROW, NETWORK]
  ): general.target.CreateArrowFromSource[Target, ADDRESS, ARROW, NETWORK] with

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