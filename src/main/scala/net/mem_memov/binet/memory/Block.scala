package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.live.DefaultBlock

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
trait Block:

  def write(
    position: UnsignedByte,
    content: UnsignedByte
  ): Block

  def read(
    position: UnsignedByte
  ): UnsignedByte