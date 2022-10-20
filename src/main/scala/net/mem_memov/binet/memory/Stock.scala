package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.stock.DefaultStock

/**
 * Stock connects an element with other elements on a lower level building a tree-like structure.
 */
trait Stock:

  def write(
    index: UnsignedByte,
    destination: Address,
    content: Address
  ): Either[String, Stock.Write]

  def read(
    index: UnsignedByte,
    origin: Address
  ): Either[String, Address]

object Stock:

  case class Write(stock: Stock, depth: Depth)


