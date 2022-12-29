package net.mem_memov.binet.hexagon.general.dotReference

trait InDot[DOT_REFERENCE]:

  def f(
    dotReference: DOT_REFERENCE
  ): Boolean

  extension (dotReference: DOT_REFERENCE)

    def inDot: Boolean =

      f(dotReference)
