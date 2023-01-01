package net.mem_memov.binet.hexagon.general.factory

trait MakeSuccessor[FACTORY, DOT, SUCCESSOR]:

  def f(
    dot: DOT
  ): SUCCESSOR

  extension (factory: FACTORY)

    def makeSuccessor(
      dot: DOT
    ): SUCCESSOR =

      f(dot)
