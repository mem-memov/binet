package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.block._

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
case class SpecificBlock(
  space: Vector[UnsignedByte]
)

object SpecificBlock:

  given BlockEmptyChecker[SpecificBlock] with

    override
    def isBlockEmpty(
      block: SpecificBlock
    ): Boolean =

      block.space.forall(_.atMinimum)

  given BlockReader[SpecificBlock] with

    override
    def readBlock(
      block: SpecificBlock,
      position: UnsignedByte
    ): UnsignedByte =

      block.space(position.toInt)

  given BlockWriter[SpecificBlock] with

    override
    def writeBlock(
      block: SpecificBlock,
      position: UnsignedByte,
      content: UnsignedByte
    ): SpecificBlock =

      block.copy(space = block.space.updated(position.toInt, content))