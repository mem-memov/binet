package net.mem_memov.binet.hexagon.general.entry

trait GetPath[ENTRY, ADDRESS]:
  
  def f(
    entry: ENTRY
  ): ADDRESS
  
  extension (entry: ENTRY)
    
    def getPath: ADDRESS =

      f(entry)
