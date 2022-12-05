package net.mem_memov.binet.hexagon.general.arrow

trait HasSourceDot[ARROW]:

  def f(
    arrow: ARROW
  ): Boolean

  extension (arrow: ARROW)

    def hasSourceDot: Boolean =

      f(arrow)
