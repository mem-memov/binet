package net.mem_memov.binet.hexagon.general.factory

trait MakeSource[FACTORY, DOT, SOURCE]:

  def f(
    dot: DOT
  ): SOURCE

  extension (factory: FACTORY)

    def makeSource(
      dot: DOT
    ): SOURCE =

      f(dot)
