package net.mem_memov.binet.memory.level

import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.{Address, Block, Depth, Element, Level, Stock, Store}
import net.mem_memov.binet.memory.depth.DefaultDepth
import net.mem_memov.binet.memory.stock.DefaultStock
import net.mem_memov.binet.memory.store.DefaultStore
import net.mem_memov.binet.memory.element.DefaultElement

case class DefaultLevel(number: Int) extends Level:

  override
  def createStore(): Store =
    DefaultStore(
      Vector.fill[Block](number + 1)(DefaultBlock.empty)
    )

  override
  def createStock(): Stock =
    val nextLevel = this.copy(number = number + 1)
    DefaultStock(
      Vector.fill[Element](Level.size)(DefaultElement(nextLevel, Option.empty, Option.empty))
    )

  override
  def toDepth: Depth =
    DefaultDepth(number)
