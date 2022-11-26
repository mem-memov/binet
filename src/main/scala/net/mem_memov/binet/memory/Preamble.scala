package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.specific.address.specific.{Orderer, Padder, Resizer, Trimmer}
import net.mem_memov.binet.memory.specific.element.specific.{Reader, Writer}
import net.mem_memov.binet.memory.specific.inventory.specific.{Argument, Walker}
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Checker

object Preamble:

  given factory: specific.Factory = new specific.Factory

//  def createInventory(): specific.Inventory = factory.emptyInventory()
//
//  def getStartAddress: specific.Address = factory.zeroAddress()



  export specific.address.specific.Padder.given
  export specific.address.specific.Trimmer.given
  given specific.address.specific.Trimmer = new specific.address.specific.Trimmer
  export specific.address.specific.Orderer.given
  given specific.address.specific.Orderer = new specific.address.specific.Orderer
  export specific.address.specific.Resizer.given
  given specific.address.specific.Resizer = new specific.address.specific.Resizer
  export specific.Address.given
  
  export specific.Block.given
  
  export specific.Content.given
  
  export specific.element.specific.Reader.given
  export specific.element.specific.Writer.given
  given specific.element.specific.Writer = new specific.element.specific.Writer
  export specific.Element.given
  
  export specific.Factory.given
  
  export specific.inventory.specific.argument.specific.Checker.given
  given specific.inventory.specific.argument.specific.Checker = new specific.inventory.specific.argument.specific.Checker
  export specific.inventory.specific.argument.specific.Trimmer.given
  given argumentTrimmer: specific.inventory.specific.argument.specific.Trimmer = new specific.inventory.specific.argument.specific.Trimmer
  export specific.inventory.specific.Argument.given
  given specific.inventory.specific.Argument = new specific.inventory.specific.Argument
  export specific.inventory.specific.Walker.given
  export specific.Inventory.given
  
  export specific.Path.given
  
  export specific.Stock.given
  
  export specific.store.specific.Trimmer.given
  given storeTrimmer: specific.store.specific.Trimmer = new specific.store.specific.Trimmer
  export specific.Store.given



