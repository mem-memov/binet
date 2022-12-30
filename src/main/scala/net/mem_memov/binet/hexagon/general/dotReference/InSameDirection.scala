package net.mem_memov.binet.hexagon.general.dotReference

trait InSameDirection[DOT_REFERENCE]:

  def f(
    dotReference: DOT_REFERENCE,
    theOther: DOT_REFERENCE
  ): Boolean

  extension (dotReference: DOT_REFERENCE)

    def inSameDirection(
      theOther: DOT_REFERENCE
    ): Boolean =

      f(dotReference, theOther)
