package net.mem_memov.binet.hexagon.general.vertex

trait ToSource[VERTEX, SOURCE]:

  def f(
    vertex: VERTEX
  ): Either[String, SOURCE]

  extension (vertex: VERTEX)

    def toSource: Either[String, SOURCE] =

      f(vertex)
