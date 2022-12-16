package net.mem_memov.binet.hexagon.general.source

trait CountTargets[SOURCE, ADDRESS]:
  
  def f(
    source: SOURCE
  ): ADDRESS
  
  extension (source: SOURCE)
    
    def countTargets: ADDRESS =

      f(source)