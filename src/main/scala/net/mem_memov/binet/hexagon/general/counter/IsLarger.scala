package net.mem_memov.binet.hexagon.general.counter

trait IsLarger[COUNTER]:

  def f(
    counter: COUNTER,
    theOther: COUNTER
  ): Boolean

  extension (counter: COUNTER)

    def isLarger(
      theOther: COUNTER
    ): Boolean =

      f(counter, theOther)
