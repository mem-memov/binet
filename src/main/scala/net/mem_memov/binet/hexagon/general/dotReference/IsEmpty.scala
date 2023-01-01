package net.mem_memov.binet.hexagon.general.dotReference

trait IsEmpty[DOT_REFERENCE]:

  def f(
    dotReference: DOT_REFERENCE
  ): Boolean

  extension (dotReference: DOT_REFERENCE)

    def isEmpty: Boolean =

      f(dotReference)
