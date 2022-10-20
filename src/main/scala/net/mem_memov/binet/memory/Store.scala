package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.store.DefaultStore

/**
 * Store is capable of storing any address of its level and the levels above.
 * All stores have the same height, but a different width which depends on the level.
 * The deeper the level the wider are the stores in order to accommodate larger addresses.
 */
trait Store:

  def write(
    destination: UnsignedByte,
    content: Address
  ): Either[String, Store]

  def read(
    origin: UnsignedByte
  ): Address

  def expand(
    minimumLength: Int
  ): Store

  def padBig(
    content: Address
  ): Either[String, Address]
