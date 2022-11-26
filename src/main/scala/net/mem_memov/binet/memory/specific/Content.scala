package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general

case class Content(
  indices: Vector[general.UnsignedByte]
)

object Content:

  given [BLOCK, FACTORY](using
    => general.factory.EmptyBlock[FACTORY, BLOCK]
  )(using
    factory: FACTORY
  ):general.content.SupplementBlocks[Content, BLOCK] with

    override
    def f(
      content: Content,
      targetLength: Int
    ): Vector[BLOCK] =

      if targetLength < content.indices.length then
        (1 to content.indices.length - targetLength).map(_ => factory.emptyBlock()).toVector
      else
        Vector.empty[BLOCK]

  given [ADDRESS, FACTORY](using
    => general.factory.MakeAddress[FACTORY, ADDRESS]
  )(using
    factory: FACTORY
  ): general.content.ToAddress[Content, ADDRESS] with

    override
    def f(
      content: Content
    ): ADDRESS =

      factory.makeAddress(
        content.indices.toList.reverse
      )

  given [BLOCK](using
    => general.block.Write[BLOCK]
  ): general.content.Write[Content, BLOCK] with

    override
    def f(
      content: Content,
      contentIndex: Integer,
      blockIndex: general.UnsignedByte,
      block: BLOCK
    ): BLOCK =

      if contentIndex >= content.indices.length then
        block.write(blockIndex, general.UnsignedByte.minimum)
      else
        block.write(blockIndex, content.indices(contentIndex.toInt))
