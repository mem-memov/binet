package net.mem_memov.binet.memory.factory

import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory.store.DefaultStore
import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.defaultFactory._

trait DefaultFactory
  extends BlockFactory
  with ElementFactory
  with LevelFactory
  with StockFactory
  with StoreFactory

object DefaultFactory:

  def apply(): DefaultFactory = new DefaultFactory:

    given blockFactory: BlockFactory = BlockFactory()
    given elementFactory: ElementFactory = ElementFactory()
    given levelFactory: LevelFactory = LevelFactory()
    given stockFactory: StockFactory = StockFactory()
    given storeFactory: StoreFactory = StoreFactory()

    override def makeBlock(space: Vector[UnsignedByte]): Block =
      blockFactory.makeBlock(space)

    override def makeElement(level: Level): Element =
      elementFactory.makeElement(level)

    override def makeLevel(number: Int): Level =
      levelFactory.makeLevel(number)

    override def makeStock(size: Int, level: Level): Stock =
      stockFactory.makeStock(size, level)

    override def makeStore(size: Int): Store =
      storeFactory.makeStore(size)