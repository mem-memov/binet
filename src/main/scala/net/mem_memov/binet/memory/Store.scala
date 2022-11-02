package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.{TreeBlock, TreeStore}

/**
 * Store is capable of storing addresses.
 */
trait Store:

  def write(
    destination: UnsignedByte,
    content: Content
  ): Store

  def read(
    origin: UnsignedByte
  ): Address

