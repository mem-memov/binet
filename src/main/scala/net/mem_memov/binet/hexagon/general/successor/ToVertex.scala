package net.mem_memov.binet.hexagon.general.successor

trait ToVertex[SUCCESSOR, VERTEX]:

  def f(
    successor: SUCCESSOR
  ): VERTEX

  extension (successor: SUCCESSOR)

    def toVertex: VERTEX =

      f(successor)
