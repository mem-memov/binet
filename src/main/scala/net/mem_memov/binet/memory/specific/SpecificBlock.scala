package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

/**
 * Block has the property that it provides content at any possible position.
 * The possibilities are limited by the position type.
 * Blocks are for storing separate indices of an address.
 * At each level blocks are organized into stores.
 */
case class SpecificBlock(
  space: Vector[general.UnsignedByte]
)

object SpecificBlock:

  lazy val emptyBlock: SpecificBlock =
    SpecificBlock(
      Vector.fill(general.UnsignedByte.maximum.toInt + 1)(general.UnsignedByte.minimum)
    )

  given general.block.IsEmpty[SpecificBlock] with

    override
    def isBlockEmpty(
      block: SpecificBlock
    ): Boolean =

      block.space.forall(_.atMinimum)

  given general.block.Read[SpecificBlock] with

    override
    def readBlock(
      block: SpecificBlock,
      position: general.UnsignedByte
    ): general.UnsignedByte =

      block.space(position.toInt)

  given general.block.Write[SpecificBlock] with

    override
    def writeBlock(
      block: SpecificBlock,
      position: general.UnsignedByte,
      content: general.UnsignedByte
    ): SpecificBlock =

      block.copy(space = block.space.updated(position.toInt, content))