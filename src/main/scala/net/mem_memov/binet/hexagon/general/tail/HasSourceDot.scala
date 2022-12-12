package net.mem_memov.binet.hexagon.general.tail

trait HasSourceDot[TAIL, DOT]:

  def f(
    tail: TAIL,
    dot: DOT
  ): Boolean

  extension (tail: TAIL)

    def hasSourceDot(
      dot: DOT
    ): Boolean =

      f(tail, dot)
