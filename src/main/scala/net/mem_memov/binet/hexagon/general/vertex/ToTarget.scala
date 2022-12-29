package net.mem_memov.binet.hexagon.general.vertex

trait ToTarget[VERTEX, TARGET]:

  def f(
    vertex: VERTEX
  ): Either[String, TARGET]

  extension (vertex: VERTEX)

    def toTarget: Either[String, TARGET] =

      f(vertex)
