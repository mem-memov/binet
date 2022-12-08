package net.mem_memov.binet.hexagon.general.arrow

trait ToTail[ARROW, TAIL]:

  def f(
    arrow: ARROW
  ): TAIL

  extension (arrow: ARROW)

    def toTail: TAIL =

      f(arrow)
