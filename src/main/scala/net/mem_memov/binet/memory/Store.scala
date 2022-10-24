package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.store.DefaultStore

/**
 * Store is capable of storing addresses.
 */
trait Store extends WritableStore with ReadableStore with ExpandableStore

trait WritableStore:

  def write(
    destination: UnsignedByte,
    content: ZippingAddress
  ): Either[String, Store]

trait ReadableStore:

  def read(
    origin: UnsignedByte
  ): Address

trait ExpandableStore:

  def expand(
    minimumLength: Int
  ): Store