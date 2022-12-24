package net.mem_memov.binet.hexagon.general.dotReference

trait InArrow[DOT_REFERENCE]:

  def f(
    dotReference: DOT_REFERENCE
  ): Boolean

  extension (dotReference: DOT_REFERENCE)

    def inArrow: Boolean =

      f(dotReference)
