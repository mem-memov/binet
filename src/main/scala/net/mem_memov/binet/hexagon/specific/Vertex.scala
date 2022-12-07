package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class Vertex(
  dot: Dot
)

object Vertex:

  given [FACTORY, SOURCE](using
    general.factory.MakeSource[FACTORY, Dot, SOURCE]
  )(using
    factory: FACTORY
  ): general.vertex.ToSource[Vertex, SOURCE] with

    override
    def f(
      vertex: Vertex
    ): SOURCE =

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
