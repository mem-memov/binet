package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory._

case class TreeContent(
  indices: Vector[UnsignedByte]
)(using
  addressFactory: AddressFactory,
  blockFactory: BlockFactory
) extends Content:

  override
  def supplementBlocks(
    targetLength: Int
  ): Vector[Block] =

    if targetLength < indices.length then
      (0 to indices.length - targetLength).map(_ => blockFactory.emptyBlock).toVector
    else
      Vector.empty[Block]

  override
  def write(
    contentIndex: Integer,
    blockIndex: UnsignedByte,
    block: Block
  ): Block =

    if contentIndex >= indices.length then
      block.write(blockIndex, UnsignedByte.minimum)
    else
      block.write(blockIndex, indices(contentIndex.toInt))

  override
  def toAddress: Address =

    addressFactory.makeAddress(indices.toList)