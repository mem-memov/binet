package net.mem_memov.binet.hexagon.general.counter

trait Increment[COUNTER]:

  def f(
    counter: COUNTER
  ): COUNTER

  extension (counter: COUNTER)

    def increment: COUNTER =

      f(counter)
