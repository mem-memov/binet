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


//  given net_mem_memov_binet_memory_specific_address_specific_Orderer: Orderer = new Orderer
//  //  export Orderer.given
//
//  given net_mem_memov_binet_memory_specific_address_specific_Padder: Padder = new Padder
//  //  export Padder.given
//
//  given net_mem_memov_binet_memory_specific_address_specific_Resizer: Resizer = new Resizer
//  //  export Resizer.given
//
//  given net_mem_memov_binet_memory_specific_address_specific_Trimmer: Trimmer = new Trimmer
////  export Trimmer.given
//
//  given net_mem_memov_binet_memory_specific_element_specific_Reader: Reader = new Reader
//  //  export Reader.given
//
//  given net_mem_memov_binet_memory_specific_element_specific_Writer: Writer = new Writer
////  export Writer.given
//
//  given net_mem_memov_binet_memory_specific_inventory_specific_argument_specific_Checker: Checker = new Checker
//  //  export Checker.given
//
//  given net_mem_memov_binet_memory_specific_inventory_specific_argument_specific_Trimmer: net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Trimmer = new net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Trimmer
////  export Trimmer.given
//
//  given net_mem_memov_binet_memory_specific_inventory_specific_Argument: Argument = new Argument
//  //  export Argument.given
//
//  given net_mem_memov_binet_memory_specific_inventory_specific_Walker: Walker = new Walker
////  export Walker.given
//
//  export specific.Address.given
//  export specific.Block.given
//  export specific.Content.given
//  export specific.Element.given
//  export specific.Factory.given
//  export specific.Path.given
//  export specific.Stock.given
//  export specific.Store.given


//  export specific.Preamble.given

//  export specific.address.specific.Padder.given
  export specific.address.specific.Trimmer.given
  given specific.address.specific.Trimmer = new specific.address.specific.Trimmer
  export specific.address.specific.Orderer.given
  given specific.address.specific.Orderer = new specific.address.specific.Orderer
  export specific.address.specific.Resizer.given
  given specific.address.specific.Resizer = new specific.address.specific.Resizer
//  export specific.Address.given
//  export specific.Block.given
//  export specific.Content.given
//  export specific.element.specific.Reader.given
  export specific.element.specific.Writer.given
//  given specific.element.specific.Writer = new specific.element.specific.Writer
  export specific.Element.given
//  given specific.Element = new specific.Element(None, None)
  export specific.Factory.given
  export specific.inventory.specific.argument.specific.Checker.given
  given specific.inventory.specific.argument.specific.Checker = new specific.inventory.specific.argument.specific.Checker
  export specific.inventory.specific.argument.specific.Trimmer.given
  given argumentTrimmer: specific.inventory.specific.argument.specific.Trimmer = new specific.inventory.specific.argument.specific.Trimmer
  export specific.inventory.specific.Argument.given
//  export specific.inventory.specific.Walker.given
//  export specific.Inventory.given
  export specific.Path.given
//  export specific.Stock.given
//  export specific.store.specific.Trimmer.given
//  export specific.Store.given



