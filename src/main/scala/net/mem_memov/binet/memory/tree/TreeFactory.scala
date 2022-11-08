package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory._

/**
 * API for library clients to start with
 */
trait TreeFactory:

  lazy val emptyInventory: TreeInventory

object TreeFactory:

  given addressFactory: AddressFactory = AddressFactory()
  given blockFactory: BlockFactory = BlockFactory()
  given contentFactory: ContentFactory = ContentFactory()
  given elementFactory: ElementFactory = ElementFactory()
  import net.mem_memov.binet.memory.tree.treeFactory.inventoryFactory.FactoryOfInventories.inventoryFactory
  given pathFactory: PathFactory = PathFactory()
  given stockFactory: StockFactory = StockFactory()
  given storeFactory: StoreFactory = StoreFactory()
  given traversalFactory: TraversalFactory = TraversalFactory()

  val emptyAddress: Address = addressFactory.zeroAddress

  def apply(): TreeFactory = new TreeFactory:

    lazy val emptyInventory: TreeInventory = summon[InventoryFactory[TreeInventory]].emptyInventory
