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

  given net_mem_memov_binet_memory_specific_Block_IsEmpty: general.block.IsEmpty[Block] with

    override
    def f(
      block: Block
    ): Boolean =

      block.space.forall(_.atMinimum)

  given net_mem_memov_binet_memory_specific_Block_Read: general.block.Read[Block] with

    override
    def f(
      block: Block,
      position: general.UnsignedByte
    ): general.UnsignedByte =

      block.space(position.toInt)

  given net_mem_memov_binet_memory_specific_Block_Write: general.block.Write[Block] with

    override
    def f(
      block: Block,
      position: general.UnsignedByte,
      content: general.UnsignedByte
    ): Block =

      block.copy(space = block.space.updated(position.toInt, content))