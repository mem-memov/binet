package net.mem_memov.binet.memory.level

import net.mem_memov.binet.memory.element.StoreElement
import net.mem_memov.binet.memory.{Address, Block, Element, Level, Stock, Store}

class DefaultLevel(number: Int) extends Level:

  override
  def createStore(): Store =
    Store(
      Vector.fill[Block](number + 1)(Block())
    )

  override
  def createStock(store: Store): Stock =
    Stock(
      Vector.fill[Element](Level.size)(new StoreElement(this, store.enlarge()))
    )

  override
  def padBig(content: Address): Either[String, Address] =
    content.padBig(number + 1)

  override
  def increment(): Level =
    Level(number + 1)
