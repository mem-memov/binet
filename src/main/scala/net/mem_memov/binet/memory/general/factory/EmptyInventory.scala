package net.mem_memov.binet.memory.general.factory

trait EmptyInventory[FACTORY, INVENTORY]:

  private[EmptyInventory]
  def f(): INVENTORY
  
  extension (factory: FACTORY)
  
    def emptyInventory(): INVENTORY =
  
      f()