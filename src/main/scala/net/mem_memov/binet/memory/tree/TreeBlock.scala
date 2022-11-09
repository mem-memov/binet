package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*

case class TreeBlock(
  space: Vector[UnsignedByte]
)

object TreeBlock:

  given Block[TreeBlock] with

    override
    def isBlockEmpty(
      block: TreeBlock
    ): Boolean =

      block.space.forall(_.atMinimum)

    override
    def readBlock(
      block: TreeBlock,
      position: UnsignedByte
    ): UnsignedByte =

      block.space(position.toInt)

    override
    def writeBlock(
      block: TreeBlock,
      position: UnsignedByte,
      content: UnsignedByte
    ): TreeBlock =

      block.copy(space = block.space.updated(position.toInt, content))
