package net.mem_memov.binet.memory.factory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.defaultFactory._

/**
 * API for library clients to start with
 */
trait DefaultFactory extends InventoryFactory

object DefaultFactory:

  val emptyAddress: Address = AddressFactory().zeroAddress

  def apply(): DefaultFactory = new DefaultFactory:

    given addressFactory: AddressFactory = AddressFactory()
    given blockFactory: BlockFactory = BlockFactory()
    given elementFactory: ElementFactory = ElementFactory()
    given inventoryFactory: InventoryFactory = InventoryFactory()
    given stockFactory: StockFactory = StockFactory()
    given storeFactory: StoreFactory = StoreFactory()

    lazy val emptyInventory: Inventory = inventoryFactory.emptyInventory
