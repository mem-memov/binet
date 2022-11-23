package net.mem_memov.binet.memory

object Preamble:

  given factory: specific.Factory = new specific.Factory

//  def createInventory(): specific.Inventory = factory.emptyInventory()
//
//  def getStartAddress: specific.Address = factory.zeroAddress()

  export specific.Preamble.given
