package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory._

/**
 * API for library clients to start with
 */
trait TreeFactory extends InventoryFactory

object TreeFactory:

  val emptyAddress: Address = AddressFactory().zeroAddress

  def apply(): TreeFactory = new TreeFactory:

    given addressFactory: AddressFactory = AddressFactory()
    given blockFactory: BlockFactory = BlockFactory()
    given elementFactory: ElementFactory = ElementFactory()
    given inventoryFactory: InventoryFactory = InventoryFactory()
    given stockFactory: StockFactory = StockFactory()
    given storeFactory: StoreFactory = StoreFactory()

    lazy val emptyInventory: Inventory = inventoryFactory.emptyInventory
