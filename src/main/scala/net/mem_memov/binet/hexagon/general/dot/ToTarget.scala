package net.mem_memov.binet.hexagon.general.dot

trait ToTarget[DOT, TARGET]:

  def f(
    dot: DOT
  ): TARGET

  extension (dot: DOT)

    def toTarget: TARGET =

      f(dot)
