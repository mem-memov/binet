package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Vertex(
  dotReference: DotReference
)

object Vertex:

  given [DOT, NETWORK, SOURCE](using
    general.network.ReadDot[NETWORK, DotReference, DOT],
    general.dot.ToSource[DOT, SOURCE]
  ): general.vertex.ToSource[Vertex, SOURCE] with

    override
    def f(
      vertex: Vertex,
      network: NETWORK
    ): Either[String, SOURCE] =

      for {
        dot <- network.readDot(vertex.dotReference)
      } yield dot.toSource

  given [DOT, NETWORK, TARGET](using
    general.network.ReadDot[NETWORK, DotReference, DOT],
    general.dot.ToTarget[DOT, TARGET]
  ): general.vertex.ToTarget[Vertex, TARGET] with

    override
    def f(
      vertex: Vertex,
      network: NETWORK
    ): Either[String, TARGET] =

      for {
        dot <- network.readDot(vertex.dotReference)
      } yield dot.toTarget
