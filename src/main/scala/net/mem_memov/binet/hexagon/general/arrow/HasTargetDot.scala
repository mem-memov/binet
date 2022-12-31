package net.mem_memov.binet.hexagon.general.arrow

trait HasTargetDot[ARROW, DOT]:

  def f(
    arrow: ARROW,
    targetDot: DOT
  ): Boolean

  extension (arrow: ARROW)

    def hasTargetDot(
      targetDot: DOT
    ): Boolean =

      f(arrow, targetDot)
