package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.TreeStock

import scala.collection.immutable.Queue

/**
 * Stock connects an element with other elements on a lower level building a tree-like structure.
 */
trait Stock:

  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, Stock]

  def read(
    index: UnsignedByte,
    origin: Address
  ): Either[String, Address]




