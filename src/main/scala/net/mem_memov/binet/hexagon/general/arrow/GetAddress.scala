package net.mem_memov.binet.hexagon.general.arrow

trait GetAddress[ARROW, ADDRESS]:

  def f(
    arrow: ARROW
  ): ADDRESS

  extension (arrow: ARROW)

    def getAddress: ADDRESS =

      f(arrow)
