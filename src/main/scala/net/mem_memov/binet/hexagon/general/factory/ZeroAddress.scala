package net.mem_memov.binet.hexagon.general.factory

trait ZeroAddress[FACTORY, ADDRESS]:
  
  def f(): ADDRESS
  
  extension (factory: FACTORY)
    
    def zeroAddress: ADDRESS