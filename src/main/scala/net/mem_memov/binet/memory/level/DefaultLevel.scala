package net.mem_memov.binet.memory.level

import net.mem_memov.binet.memory.{Address, Block, Depth, Element, Level, Stock, Store}
import net.mem_memov.binet.memory.depth.DefaultDepth

case class DefaultLevel(number: Int) extends Level:

  override
  def createStore(): Store =
    Store(
      Vector.fill[Block](number + 1)(Block())
    )

  override
  def createStock(): Stock =
    val nextLevel = this.copy(number = number + 1)
    Stock(
      Vector.fill[Element](Level.size)(Element(nextLevel, Option.empty, Option.empty))
    )

  override
  def padBig(content: Address): Either[String, Address] =
    content.padBig(number + 1)

  override
  def toDepth: Depth =
    DefaultDepth(number)
