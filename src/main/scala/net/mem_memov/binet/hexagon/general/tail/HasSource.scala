package net.mem_memov.binet.hexagon.general.tail

trait HasSource[TAIL, SOURCE]:

  def f(
    tail: TAIL,
    source: SOURCE
  ): Boolean

  extension (tail: TAIL)

    def hasSource(
      source: SOURCE
    ): Boolean =

      f(tail, source)
