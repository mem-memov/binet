package net.mem_memov.binet.hexagon.general.arrow

trait HasTargetDot[ARROW]:

  def f(
    arrow: ARROW
  ): Boolean

  extension (arrow: ARROW)

    def hasTargetDot: Boolean =

      f(arrow)
