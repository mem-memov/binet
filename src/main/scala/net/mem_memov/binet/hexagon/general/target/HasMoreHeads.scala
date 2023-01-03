package net.mem_memov.binet.hexagon.general.target

trait HasMoreHeads[TARGET, COUNTER]:

  def f(
    target: TARGET,
    counter: COUNTER
  ): Boolean

  extension (target: TARGET)

    def hasMoreHeads(
      counter: COUNTER
    ): Boolean =

      f(target, counter)
