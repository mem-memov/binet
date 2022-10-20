package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.block.DefaultBlock

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
trait Block:

  def read(
    position: UnsignedByte
  ): UnsignedByte

  def write(
    position: UnsignedByte,
    content: UnsignedByte
  ): Block

object Block:

  def apply(): Block =
    Block(
      Vector.fill(UnsignedByte.maximum.toInt + 1)(UnsignedByte.minimum)
    )

  def apply(space: Vector[UnsignedByte]): Block = DefaultBlock(space)
