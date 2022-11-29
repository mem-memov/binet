package net.mem_memov.binet.hexagon.general.factory

trait EmptyDictionary[FACTORY, DICTIONARY]:
  
  def f(): DICTIONARY
  
  extension (factory: FACTORY)
    
    def emptyDictionary: DICTIONARY =

      f()
