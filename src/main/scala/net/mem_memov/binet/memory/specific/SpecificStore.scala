package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.specificStore.general.trimmer.TrimmingService

case class SpecificStore(
  blocks: Vector[SpecificBlock]
)

object SpecificStore:

  lazy val emptyStore: SpecificStore =

    SpecificStore(
      Vector(SpecificBlock.emptyBlock)
    )

  given general.store.Read[SpecificStore, SpecificAddress] with

    override
    def readStore(
      store: SpecificStore,
      origin: general.UnsignedByte
    ): SpecificAddress =

      val parts = store.blocks.foldLeft(List.empty[general.UnsignedByte]) {
        case(parts, block) =>
          block.read(origin) :: parts
      }

      SpecificAddress.makeAddress(parts.reverse)

  given [TRIMMER](
    using trimmer: TRIMMER
  )(
    using TrimmingService[TRIMMER, SpecificBlock]
  ): general.store.Write[SpecificStore, SpecificContent] with

    override
    def writeStore(
      store: SpecificStore,
      destination: general.UnsignedByte,
      content: SpecificContent
    ): SpecificStore =

      val appendedBlocks = content.supplementBlocks(store.blocks.length)
      val expandedBlocks = store.blocks.appendedAll(appendedBlocks)

      val modifiedBlocks = expandedBlocks.zipWithIndex.map { case (block, index) =>
        content.write(index, destination, block)
      }

      val contractedBlocks = trimmer.trimRight(modifiedBlocks)

      store.copy(blocks = contractedBlocks)
