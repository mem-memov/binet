package net.mem_memov.binet.memory

import zio.*

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
class Block(
  private val space: Vector[UnsignedByte]
):

  def read(position: UnsignedByte): UnsignedByte =
    space(position.toInt)

  def write(position: UnsignedByte, content: UnsignedByte): Block =
    Block(space.updated(position.toInt, content))

object Block:

  def apply(): Block =
    new Block(
      Vector.fill(UnsignedByte.maximum.toInt + 1)(UnsignedByte.minimum)
    )

  def apply(space: Vector[UnsignedByte]): Block =
    new Block(space)
