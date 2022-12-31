package net.mem_memov.binet.hexagon.general.factory

trait TakeZeroAddress[FACTORY, ADDRESS]:
  
  def f(): ADDRESS
  
  extension (factory: FACTORY)
    
    def takeZeroAddress: ADDRESS =

      f()