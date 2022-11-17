package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class Content(
  indices: Vector[general.UnsignedByte]
)

object Content:

  given general.content.SupplementBlocks[Content, Block] with

    override
    def supplementContentBlocks(
      content: Content,
      targetLength: Int
    ): Vector[Block] =

      if targetLength < content.indices.length then
        (1 to content.indices.length - targetLength).map(_ => Block.emptyBlock).toVector
      else
        Vector.empty[Block]

  given general.content.ToAddress[Content, Address] with

    override
    def contentToAddress(
      content: Content
    ): Address =

      Address.makeAddress(
        content.indices.toList.reverse
      )

  given [
    BLOCK
  ](using
    general.block.Write[BLOCK]
  ): general.content.Write[Content, BLOCK] with

    override
    def writeContent(
      content: Content,
      contentIndex: Integer,
      blockIndex: general.UnsignedByte,
      block: BLOCK
    ): BLOCK =

      if contentIndex >= content.indices.length then
        block.write(blockIndex, general.UnsignedByte.minimum)
      else
        block.write(blockIndex, content.indices(contentIndex.toInt))
