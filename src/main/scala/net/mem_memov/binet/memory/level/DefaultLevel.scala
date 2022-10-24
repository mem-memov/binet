package net.mem_memov.binet.memory.level

import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.defaultFactory._
import net.mem_memov.binet.memory.depth.DefaultDepth
import net.mem_memov.binet.memory.factory.DefaultFactory
import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory.store.DefaultStore
import net.mem_memov.binet.memory.element.DefaultElement

case class DefaultLevel(
  number: Int
)(using
  elementFactory: ElementFactory,
  levelFactory: LevelFactory,
  stockFactory: StockFactory,
  storeFactory: StoreFactory
) extends Level:

  override
  def createStore(): Store =
    storeFactory.makeStore(number + 1)

  override
  def createStock(): Stock =
    val nextLevel = levelFactory.makeLevel(number + 1)
    stockFactory.makeStock(DefaultLevel.size, nextLevel)

object DefaultLevel:

  lazy val size: Int = UnsignedByte.maximum.toInt + 1

