package net.mem_memov.binet.hexagon.general.dot

trait IsReferencedBy[DOT, DOT_REFERENCE]:

  def f(
    dot: DOT,
    dotReference: DOT_REFERENCE
  ): Boolean

  extension (dot: DOT)

    def isReferencedBy(
      dotReference: DOT_REFERENCE
    ): Boolean =

      f(dot, dotReference)
