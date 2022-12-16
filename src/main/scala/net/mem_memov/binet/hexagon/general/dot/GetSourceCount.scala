package net.mem_memov.binet.hexagon.general.dot

trait GetSourceCount[DOT, ADDRESS]:

  def f(
    dot: DOT
  ): ADDRESS

  extension (dot: DOT)

    def getSourceCount: ADDRESS =

      f(dot)