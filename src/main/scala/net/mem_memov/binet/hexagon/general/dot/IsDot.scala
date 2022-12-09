package net.mem_memov.binet.hexagon.general.dot

trait IsDot[DOT]:

  def f(
    dot: DOT
  ): Boolean
  
  extension (dot: DOT)

    def isDot: Boolean =

      f(dot)