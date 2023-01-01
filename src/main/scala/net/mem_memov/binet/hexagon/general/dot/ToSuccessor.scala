package net.mem_memov.binet.hexagon.general.dot

trait ToSuccessor[DOT, SUCCESSOR]:

  def f(
    dot: DOT
  ): SUCCESSOR

  extension (dot: DOT)

    def toSuccessor: SUCCESSOR =

      f(dot)
