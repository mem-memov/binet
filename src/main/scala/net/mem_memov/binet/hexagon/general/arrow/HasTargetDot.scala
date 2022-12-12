package net.mem_memov.binet.hexagon.general.arrow

trait HasTargetDot[ARROW, ADDRESS]:

  def f(
    arrow: ARROW,
    targetDotAddress: ADDRESS
  ): Boolean

  extension (arrow: ARROW)

    def hasTargetDot(
      targetDotAddress: ADDRESS
    ): Boolean =

      f(arrow, targetDotAddress)
