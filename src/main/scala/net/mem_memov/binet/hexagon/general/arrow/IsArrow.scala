package net.mem_memov.binet.hexagon.general.arrow

trait IsArrow[ARROW]:

  def f(
    arrow: ARROW
  ): Boolean

  extension (arrow: ARROW)

    def isArrow: Boolean =

      f(arrow)
