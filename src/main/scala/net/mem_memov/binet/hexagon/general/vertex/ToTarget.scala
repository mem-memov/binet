package net.mem_memov.binet.hexagon.general.vertex

trait ToTarget[VERTEX, TARGET]:

  def f(
    vertex: VERTEX
  ): TARGET

  extension (vertex: VERTEX)

    def toTarget: TARGET =

      f(vertex)
