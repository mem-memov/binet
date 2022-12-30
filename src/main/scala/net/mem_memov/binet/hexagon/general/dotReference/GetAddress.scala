package net.mem_memov.binet.hexagon.general.dotReference

trait GetAddress[DOT_REFERENCE, ADDRESS]:
  
  def f(
    dotReference: DOT_REFERENCE
  ): ADDRESS
  
  extension (dotReference: DOT_REFERENCE)
    
    def getAddress: ADDRESS =

      f(dotReference)
