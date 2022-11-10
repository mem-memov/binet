package net.mem_memov.binet.memoryOld.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.treeFactory.*

case class TreeContent(
  indices: Vector[UnsignedByte]
)

object TreeContent:

  given (using
    addressFactory: AddressFactory,
    blockFactory: BlockFactory
  ): Content[TreeContent, TreeAddress, TreeBlock] with

    override
    def supplementContentBlocks(
      content: TreeContent,
      targetLength: Int
    ): Vector[TreeBlock] =

      if targetLength < content.indices.length then
        (0 to content.indices.length - targetLength).map(_ => blockFactory.emptyBlock).toVector
      else
        Vector.empty[TreeBlock]

    override
    def writeContent(
      content: TreeContent,
      contentIndex: Integer,
      blockIndex: UnsignedByte,
      block: TreeBlock
    ): TreeBlock =

      if contentIndex >= content.indices.length then
        block.write(blockIndex, UnsignedByte.minimum)
      else
        block.write(blockIndex, indices(contentIndex.toInt))

    override
    def toAddress: TreeAddress =

      addressFactory.makeAddress(indices.toList)