package net.mem_memov.binet.hexagon.general.factory

trait MakeVertex[FACTORY, DOT_REFERENCE, VERTEX]:

  def f(
    dotReference: DOT_REFERENCE
  ): VERTEX

  extension (factory: FACTORY)

    def makeVertex(
      dotreference: DOT_REFERENCE
    ): VERTEX =

      f(dotreference)
