package net.mem_memov.binet.memory.factory

import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory.store.DefaultStore
import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.defaultFactory._

trait DefaultFactory extends StockFactory with StoreFactory

object DefaultFactory:

  def apply(): DefaultFactory = new DefaultFactory:

    val stockFactory: StockFactory = StockFactory()
    def makeStock(elements: Vector[Element]): Stock = stockFactory.makeStock(elements)

    val storeFactory: StoreFactory = StoreFactory()
    def makeStore(blocks: Vector[Block]): Store = storeFactory.makeStore(blocks)