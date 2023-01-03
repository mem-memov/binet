package net.mem_memov.binet.hexagon.general.tail

trait ReferencesDot[TAIL, DOT_REFERENCE]:

  def f(
    tail: TAIL,
    dotReference: DOT_REFERENCE
  ): Boolean

  extension (tail: TAIL)

    def referencesDot(
      dotReference: DOT_REFERENCE
    ): Boolean =

      f(tail, dotReference)
