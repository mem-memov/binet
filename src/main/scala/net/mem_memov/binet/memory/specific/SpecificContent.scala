package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.content.{ContentBlockSupplementer, ContentToAddressConverter, ContentWriter}
import net.mem_memov.binet.memory.general.block.BlockWriter

case class SpecificContent(
  indices: Vector[UnsignedByte]
)

object SpecificContent:

  given ContentBlockSupplementer[SpecificContent] with

    override
    def supplementContentBlocks(
      content: SpecificContent,
      targetLength: Int
    ): Vector[SpecificBlock] =

      if targetLength < content.indices.length then
        (0 to content.indices.length - targetLength).map(_ => SpecificBlock.emptyBlock).toVector
      else
        Vector.empty[SpecificBlock]

  given ContentToAddressConverter[SpecificContent] with

    override
    def contentToAddress(
      content: SpecificContent
    ): SpecificAddress =

      SpecificAddress.makeAddress(
        content.indices.toList.reverse
      )

  given ContentWriter[SpecificContent] with

    override
    def writeContent[
      BLOCK : BlockWriter
    ](
      content: SpecificContent,
      contentIndex: Integer,
      blockIndex: UnsignedByte,
      block: BLOCK
    ): BLOCK =

      if contentIndex >= content.indices.length then
        block.write(blockIndex, UnsignedByte.minimum)
      else
        block.write(blockIndex, content.indices(contentIndex.toInt))
