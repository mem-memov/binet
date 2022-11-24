package net.mem_memov.binet.memory

object Preamble:

  given factory: specific.Factory = new specific.Factory

//  def createInventory(): specific.Inventory = factory.emptyInventory()
//
//  def getStartAddress: specific.Address = factory.zeroAddress()

  export specific.Preamble.given

//  export specific.address.specific.Padder.given
//  export specific.address.specific.Trimmer.given
//  given specific.address.specific.Orderer = new specific.address.specific.Orderer
//  export specific.address.specific.Orderer.given
//  export specific.address.specific.Resizer.given
//  export specific.Address.given
//  export specific.Block.given
//  export specific.Content.given
//  export specific.element.specific.Reader.given
//  export specific.element.specific.Writer.given
//  export specific.Element.given
//  export specific.Factory.given
//  export specific.inventory.specific.argument.specific.Checker.given
//  export specific.inventory.specific.argument.specific.Trimmer.given
//  export specific.inventory.specific.Argument.given
//  export specific.inventory.specific.Walker.given
//  export specific.Path.given
//  export specific.Stock.given
//  export specific.store.specific.Trimmer.given
//  export specific.Store.given



