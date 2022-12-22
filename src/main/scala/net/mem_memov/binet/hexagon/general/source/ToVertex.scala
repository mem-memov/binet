package net.mem_memov.binet.hexagon.general.source

trait ToVertex[SOURCE, VERTEX]:

  def f(
    source: SOURCE
  ): VERTEX

  extension (source: SOURCE)

    def toVertex: VERTEX =

      f(source)
