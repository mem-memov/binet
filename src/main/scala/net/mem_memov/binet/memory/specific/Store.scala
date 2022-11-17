package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.content.{SupplementBlocks, Write}
import net.mem_memov.binet.memory.specific.store.general.trimmer.TrimRight

case class Store(
  blocks: Vector[Block]
)

object Store:

  lazy val emptyStore: Store =

    Store(
      Vector(Block.emptyBlock)
    )

  given read: general.store.Read[Store, Address] with

    override
    def readStore(
      store: Store,
      origin: general.UnsignedByte
    ): Address =

      val parts = store.blocks.foldLeft(List.empty[general.UnsignedByte]) {
        case(parts, block) =>
          block.read(origin) :: parts
      }

      Address.makeAddress(parts)

  given write[
    TRIMMER,
    CONTENT
  ](using
    trimRight: TrimRight[TRIMMER, Block],
    supplementBlocks: SupplementBlocks[CONTENT, Block],
    write: Write[CONTENT, Block]
  )(using
    trimmer: TRIMMER
  ): general.store.Write[Store, CONTENT] with

    override
    def writeStore(
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
