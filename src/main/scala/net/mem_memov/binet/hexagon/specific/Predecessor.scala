package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Predecessor(
  dot: Dot
)

object Predecessor:

  given [NETWORK](using
    general.dot.GiveIdentifierToPredecessor[Dot, NETWORK]
  ): general.predecessor.Precede[Predecessor, Dot, NETWORK] with

    override
    def f(
      predecessor: Predecessor,
      successorDot: Dot,
      network: NETWORK
    ): Either[String, (NETWORK, Predecessor)] =

      for {
        result <- successorDot.giveIdentifierToPredecessor(predecessor.dot, network)
        (modifiedNetwork, modifiedDot) = result
      } yield
        val modifiedPredecessor = predecessor.copy(dot = modifiedDot)
        (modifiedNetwork, modifiedPredecessor)