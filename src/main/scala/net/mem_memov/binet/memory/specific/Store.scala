package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.content.{SupplementBlocks, Write}
import net.mem_memov.binet.memory.specific.store.general.trimmer.TrimRight

case class Store(
  blocks: Vector[Block]
)

object Store:

  given [ADDRESS, FACTORY](using
    general.factory.MakeAddress[FACTORY, ADDRESS]
  )(using
    factory: FACTORY
  ):general.store.Read[Store, ADDRESS] with

    override
    def f(
      store: Store,
      origin: general.UnsignedByte
    ): ADDRESS =

      val parts = store.blocks.foldLeft(List.empty[general.UnsignedByte]) {
        case(parts, block) =>
          block.read(origin) :: parts
      }

      factory.makeAddress(parts)

  given write[
    TRIMMER,
    CONTENT
  ](using
    TrimRight[TRIMMER, Block],
    SupplementBlocks[CONTENT, Block],
    Write[CONTENT, Block]
  )(using
    trimmer: TRIMMER
  ): general.store.Write[Store, CONTENT] with

    override
    def f(
      store: Store,
      destination: general.UnsignedByte,
      content: CONTENT
    ): Store =

      val appendedBlocks = content.supplementBlocks(store.blocks.length)
      val expandedBlocks = store.blocks.appendedAll(appendedBlocks)

      val modifiedBlocks = expandedBlocks.zipWithIndex.map { case (block, index) =>
        content.write(index, destination, block)
      }

      val contractedBlocks = trimmer.trimRight(modifiedBlocks)

      store.copy(blocks = contractedBlocks)
