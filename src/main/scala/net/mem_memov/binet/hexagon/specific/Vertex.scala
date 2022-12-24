package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

case class Vertex(
  address: Address
)

object Vertex:

  given [FACTORY, NETWORK, SOURCE](using
    general.factory.MakeSource[FACTORY, Dot, SOURCE],
    general.network.ReadDot
  )(using
    factory: FACTORY
  ): general.vertex.ToSource[Vertex, SOURCE] with

    override
    def f(
      vertex: Vertex,
      network: NETWORK
    ): SOURCE =

      for {
        dot <- network
      }
      
      factory.makeSource(vertex.dot)

  given [FACTORY, TARGET](using
    general.factory.MakeTarget[FACTORY, Dot, TARGET]
  )(using
    factory: FACTORY
  ): general.vertex.ToTarget[Vertex, TARGET] with

    override
    def f(
      vertex: Vertex
    ): TARGET =

      factory.makeTarget(vertex.dot)
