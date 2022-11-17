package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
case class Block(
  space: Vector[general.UnsignedByte]
)

object Block:

  lazy val emptyBlock: Block =
    Block(
      Vector.fill(general.UnsignedByte.maximum.toInt + 1)(general.UnsignedByte.minimum)
    )

  given general.block.IsEmpty[Block] with

    override
    def f(
      block: Block
    ): Boolean =

      block.space.forall(_.atMinimum)

  given general.block.Read[Block] with

    override
    def f(
      block: Block,
      position: general.UnsignedByte
    ): general.UnsignedByte =

      block.space(position.toInt)

  given general.block.Write[Block] with

    override
    def f(
      block: Block,
      position: general.UnsignedByte,
      content: general.UnsignedByte
    ): Block =

      block.copy(space = block.space.updated(position.toInt, content))