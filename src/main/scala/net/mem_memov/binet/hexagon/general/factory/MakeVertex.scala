package net.mem_memov.binet.hexagon.general.factory

trait MakeVertex[FACTORY, DOT, VERTEX]:

  def f(
    dot: DOT
  ): VERTEX

  extension (factory: FACTORY)

    def makeVertex(
      dot: DOT
    ): VERTEX =

      f(dot)
