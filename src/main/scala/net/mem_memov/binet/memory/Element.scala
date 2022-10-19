package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.element.StoreElement

/**
 * Elements build a tree-like structure.
 * Child elements are kept in a stock.
 * Data represented by addresses is kept in a store.
 * The number of addresses in the store and the number of references to other elements in the stock are the same.
 * The level determines the number of parts of addresses in the store.
 */
trait Element:
  
  def write(
    destination: ShrinkableAddress,
    content: CompoundAddress
  ): Either[String, Element]

  def read(
    origin: ShrinkableAddress
  ): Either[String, Address]

object Element:

  val root: Element = Element(Level.top, Level.top.createStore())

  def apply(level: Level, store: Store): Element = new StoreElement(level, store)

