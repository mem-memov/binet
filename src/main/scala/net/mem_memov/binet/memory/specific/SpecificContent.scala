package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class SpecificContent(
  indices: Vector[general.UnsignedByte]
)

object SpecificContent:

  given general.content.SupplementBlocks[SpecificContent, SpecificBlock] with

    override
    def supplementContentBlocks(
      content: SpecificContent,
      targetLength: Int
    ): Vector[SpecificBlock] =

      if targetLength < content.indices.length then
        (0 to content.indices.length - targetLength).map(_ => SpecificBlock.emptyBlock).toVector
      else
        Vector.empty[SpecificBlock]

  given general.content.ToAddress[SpecificContent, SpecificAddress] with

    override
    def contentToAddress(
      content: SpecificContent
    ): SpecificAddress =

      SpecificAddress.makeAddress(
        content.indices.toList.reverse
      )

  given general.content.Write[SpecificContent, SpecificBlock] with

    override
    def writeContent(
      content: SpecificContent,
      contentIndex: Integer,
      blockIndex: general.UnsignedByte,
      block: SpecificBlock
    ): SpecificBlock =

      if contentIndex >= content.indices.length then
        block.write(blockIndex, general.UnsignedByte.minimum)
      else
        block.write(blockIndex, content.indices(contentIndex.toInt))
