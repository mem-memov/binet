package net.mem_memov.binet.memory.general.factory

trait EmptyInventory[FACTORY, INVENTORY]:

  def f(): INVENTORY
  
  extension (factory: FACTORY)
  
    def emptyInventory(): INVENTORY =
  
      f()