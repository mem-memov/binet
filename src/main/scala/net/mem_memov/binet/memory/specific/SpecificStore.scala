package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.specificStore.general.trimmer.TrimmingService

case class SpecificStore(
  blocks: Vector[Block]
)

object SpecificStore:

  lazy val emptyStore: SpecificStore =

    SpecificStore(
      Vector(Block.emptyBlock)
    )

  given general.store.Read[SpecificStore, Address] with

    override
    def readStore(
      store: SpecificStore,
      origin: general.UnsignedByte
    ): Address =

      val parts = store.blocks.foldLeft(List.empty[general.UnsignedByte]) {
        case(parts, block) =>
          block.read(origin) :: parts
      }

      Address.makeAddress(parts.reverse)

  given [TRIMMER](
    using trimmer: TRIMMER
  )(
    using TrimmingService[TRIMMER, Block]
  ): general.store.Write[SpecificStore, Content] with

    override
    def writeStore(
      store: SpecificStore,
      destination: general.UnsignedByte,
      content: Content
    ): SpecificStore =

      val appendedBlocks = content.supplementBlocks(store.blocks.length)
      val expandedBlocks = store.blocks.appendedAll(appendedBlocks)

      val modifiedBlocks = expandedBlocks.zipWithIndex.map { case (block, index) =>
        content.write(index, destination, block)
      }

      val contractedBlocks = trimmer.trimRight(modifiedBlocks)

      store.copy(blocks = contractedBlocks)
