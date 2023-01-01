package net.mem_memov.binet.hexagon.general.factory

trait MakePredecessor[FACTORY, DOT, PREDECESSOR]:

  def f(
    dot: DOT
  ): PREDECESSOR

  extension (factory: FACTORY)

    def makePredecessor(
      dot: DOT
    ): PREDECESSOR =

      f(dot)
