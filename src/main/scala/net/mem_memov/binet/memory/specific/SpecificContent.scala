package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.content.{ContentBlockSupplementer, ContentToAddressConverter, ContentWriter}

case class SpecificContent(
  indices: Vector[UnsignedByte]
)

object SpecificContent:

  given ContentBlockSupplementer[SpecificContent, SpecificBlock] with

    override
    def supplementContentBlocks(
      content: SpecificContent,
      targetLength: Int
    ): Vector[SpecificBlock] =

      if targetLength < content.indices.length then
        (0 to content.indices.length - targetLength).map(_ => SpecificBlock.emptyBlock).toVector
      else
        Vector.empty[SpecificBlock]

  given ContentToAddressConverter[SpecificContent, SpecificAddress] with

    override
    def contentToAddress(
      content: SpecificContent
    ): SpecificAddress =

      SpecificAddress.makeAddress(
        content.indices.toList.reverse
      )

  given ContentWriter[SpecificContent, SpecificBlock] with

    override
    def writeContent(
      content: SpecificContent,
      contentIndex: Integer,
      blockIndex: UnsignedByte,
      block: SpecificBlock
    ): SpecificBlock =

      if contentIndex >= content.indices.length then
        block.write(blockIndex, UnsignedByte.minimum)
      else
        block.write(blockIndex, content.indices(contentIndex.toInt))
