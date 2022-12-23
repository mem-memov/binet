package net.mem_memov.binet.hexagon.general.counter

trait Decrement[COUNTER]:
  
  def f(
    counter: COUNTER
  ): COUNTER
  
  extension (counter: COUNTER)
    
    def decrement: COUNTER =

      f(counter)
