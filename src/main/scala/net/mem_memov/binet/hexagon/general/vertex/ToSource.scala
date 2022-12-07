package net.mem_memov.binet.hexagon.general.vertex

trait ToSource[VERTEX, SOURCE]:

  def f(
    vertex: VERTEX
  ): SOURCE

  extension (vertex: VERTEX)

    def toSource: SOURCE =

      f(vertex)
