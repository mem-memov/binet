package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.{TreeBlock, TreeStore}

/**
 * Store is capable of storing addresses.
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

  def foreachSlice(
    f: Array[Byte] => Unit
  ): Unit