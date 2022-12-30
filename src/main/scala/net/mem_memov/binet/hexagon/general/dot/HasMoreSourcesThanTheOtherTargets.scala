package net.mem_memov.binet.hexagon.general.dot

trait HasMoreSourcesThanTheOtherTargets[DOT]:

  def f(
    dot: DOT,
    theOther: DOT
  ): Boolean

  extension (dot: DOT)

    def hasMoreSourcesThanTheOtherTargets(
      theOther: DOT
    ): Boolean =

      f(dot, theOther)
