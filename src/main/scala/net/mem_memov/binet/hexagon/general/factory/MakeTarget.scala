package net.mem_memov.binet.hexagon.general.factory

trait MakeTarget[FACTORY, DOT, TARGET]:

  def f(
    dot: DOT
  ): TARGET

  extension (factory: FACTORY)

    def makeTarget(
      dot: DOT
    ): TARGET =

      f(dot)
