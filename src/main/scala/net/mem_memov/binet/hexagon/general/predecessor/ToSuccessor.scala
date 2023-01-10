package net.mem_memov.binet.hexagon.general.predecessor

trait ToSuccessor[PREDECESSOR, SUCCESSOR]:

  def f(
    predecessor: PREDECESSOR
  ): SUCCESSOR

  extension (predecessor: PREDECESSOR)

    def toSuccessor: SUCCESSOR =

      f(predecessor)
