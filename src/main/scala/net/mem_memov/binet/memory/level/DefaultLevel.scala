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
  stockFactory: StockFactory,
  storeFactory: StoreFactory
) extends Level:

  override
  def createStore(): Store =
    storeFactory.makeStore(
      Vector.fill[Block](number + 1)(DefaultBlock.empty)
    )

  override
  def createStock(): Stock =
    val nextLevel = this.copy(number = number + 1)
    stockFactory.makeStock(
      Vector.fill[Element](DefaultLevel.size)(DefaultElement(nextLevel, Option.empty, Option.empty))
    )

  override
  def toDepth: Depth =
    DefaultDepth(number)

object DefaultLevel:

  lazy val size: Int = UnsignedByte.maximum.toInt + 1

  def top(using factory: DefaultFactory): Level = DefaultLevel(0)(using factory, factory)
