package net.mem_memov.binet.hexagon.general.arrow

trait HasSourceDot[ARROW, DOT]:

  def f(
    arrow: ARROW,
    sourceDot: DOT
  ): Boolean

  extension (arrow: ARROW)

    def hasSourceDot(
      sourceDot: DOT
    ): Boolean =

      f(arrow, sourceDot)
