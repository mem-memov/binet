package net.mem_memov.binet.hexagon.general.arrow

trait HasSourceDot[ARROW, ADDRESS]:

  def f(
    arrow: ARROW,
    sourceDotAddress: ADDRESS
  ): Boolean

  extension (arrow: ARROW)

    def hasSourceDot(
      sourceDotAddress: ADDRESS
    ): Boolean =

      f(arrow, sourceDotAddress)
