package net.mem_memov.binet.memory.factory

import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory.store.DefaultStore
import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.defaultFactory._

/**
 * API for library clients to start with
 */
trait DefaultFactory extends InventoryFactory

object DefaultFactory:

  def apply(): DefaultFactory = new DefaultFactory:

    given addressFactory: AddressFactory = AddressFactory()
    given blockFactory: BlockFactory = BlockFactory()
    given depthFactory: DepthFactory = DepthFactory()
    given elementFactory: ElementFactory = ElementFactory()
    given inventoryFactory: InventoryFactory = InventoryFactory()
    given levelFactory: LevelFactory = LevelFactory()
    given stockFactory: StockFactory = StockFactory()
    given storeFactory: StoreFactory = StoreFactory()

    export inventoryFactory.*
