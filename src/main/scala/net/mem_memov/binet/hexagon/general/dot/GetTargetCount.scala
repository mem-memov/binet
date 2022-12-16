package net.mem_memov.binet.hexagon.general.dot

trait GetTargetCount[DOT, ADDRESS]:

  def f(
    dot: DOT
  ): ADDRESS

  extension (dot: DOT)

    def getTargetCount: ADDRESS =

      f(dot)
