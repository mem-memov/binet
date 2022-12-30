package net.mem_memov.binet.hexagon.general.dot

trait HasMoreSources[DOT, COUNTER]:

  def f(
    dot: DOT,
    counter: COUNTER
  ): Boolean

  extension (dot: DOT)

    def hasMoreSources(
      counter: COUNTER
    ): Boolean =

      f(dot, counter)
