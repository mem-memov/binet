package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Successor(
  dotReference: DotReference,
  nextDotReference: DotReference,
  sourceCounter: Counter,
  targetCounter: Counter,
  sourceArrowReference: ArrowReference,
  targetArrowReference: ArrowReference
)

object Successor:

  given [NETWORK, PREDECESSOR](using
    general.predecessor.Precede[PREDECESSOR, DotReference, NETWORK]
  ): general.successor.Follow[Successor, NETWORK, PREDECESSOR] with

    override
    def f(
      successor: Successor,
      predecessor: PREDECESSOR,
      network: NETWORK
    ): Either[String, (NETWORK, PREDECESSOR)] =

      predecessor.precede(successor.dotReference, network)

  given [FACTORY, VERTEX](using
    general.dotReference.ToVertex[DotReference, VERTEX]
  ): general.successor.ToVertex[Successor, VERTEX] with

    override
    def f(
      successor: Successor
    ): VERTEX =

      successor.dotReference.toVertex