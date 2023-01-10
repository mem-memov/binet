package net.mem_memov.binet.hexagon.general.successor

trait ToPredecessor[SUCCESSOR, PREDECESSOR]:

  def f(
    successor: SUCCESSOR
  ): PREDECESSOR

  extension (successor: SUCCESSOR)

    def toPredecessor: PREDECESSOR =

      f(successor)
