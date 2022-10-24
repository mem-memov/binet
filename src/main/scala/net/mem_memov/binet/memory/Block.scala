package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.block.DefaultBlock

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
trait Block extends WritableBlock with ReadableBlock

trait WritableBlock:

  def write(
    position: UnsignedByte,
    content: UnsignedByte
  ): Block

trait ReadableBlock:

  def read(
    position: UnsignedByte
  ): UnsignedByte