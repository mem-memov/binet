package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Successor(
  dot: Dot
)

object Successor:

  given [NETWORK, PREDECESSOR](using
    general.predecessor.Precede[PREDECESSOR, Dot, NETWORK]
  ): general.successor.Follow[Successor, NETWORK, PREDECESSOR] with

    override
    def f(
      successor: Successor,
      predecessor: PREDECESSOR,
      network: NETWORK
    ): Either[String, (NETWORK, PREDECESSOR)] =

      predecessor.precede(successor.dot, network)

  given [FACTORY, VERTEX](using
    general.dot.ToVertex[Dot, VERTEX]
  ): general.successor.ToVertex[Successor, VERTEX] with

    override
    def f(
      successor: Successor
    ): VERTEX =

      successor.dot.toVertex