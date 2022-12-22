package net.mem_memov.binet.hexagon.general.target

trait ToVertex[TARGET, VERTEX]:

  def f(
    target: TARGET
  ): VERTEX

  extension (target: TARGET)

    def toVertex: VERTEX =

      f(target)
