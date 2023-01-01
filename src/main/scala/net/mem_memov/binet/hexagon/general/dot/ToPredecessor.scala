package net.mem_memov.binet.hexagon.general.dot

trait ToPredecessor[DOT, PREDECESSOR]:

  def f(
    dot: DOT
  ): PREDECESSOR

  extension (dot: DOT)

    def toPredecessor: PREDECESSOR =

      f(dot)
