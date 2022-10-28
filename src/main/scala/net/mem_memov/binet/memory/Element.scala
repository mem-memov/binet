package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.TreeElement

/**
 * Elements build a tree-like structure.
 * Child elements are kept in a stock.
 * Data represented by addresses is kept in a store.
 * The number of addresses in the store and the number of references to other elements in the stock are the same.
 * The level determines the number of parts of addresses in the store.
 */
trait Element:

  def write(
    destination: Address,
    content: Address
  ): Either[String, (Element, Option[Element])]

  def read(
    origin: Address
  ): Either[String, Address]



