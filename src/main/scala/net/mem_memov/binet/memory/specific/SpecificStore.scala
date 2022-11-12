package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.store.{StoreReader, StoreWriter}
import net.mem_memov.binet.memory.specific.specificStore.general.trimmer.TrimmingService

case class SpecificStore(
  blocks: Vector[SpecificBlock]
)

object SpecificStore:

  lazy val emptyStore: SpecificStore =

    SpecificStore(
      Vector(SpecificBlock.emptyBlock)
    )

  given StoreReader[SpecificStore, SpecificAddress] with

    override
    def readStore(
      store: SpecificStore,
      origin: UnsignedByte
    ): SpecificAddress =

      val parts = store.blocks.foldLeft(List.empty[UnsignedByte]) {
        case(parts, block) =>
          block.read(origin) :: parts
      }

      SpecificAddress.makeAddress(parts.reverse)

  given [TRIMMER](
    using trimmer: TRIMMER
  )(
    using TrimmingService[TRIMMER, SpecificBlock]
  ): StoreWriter[SpecificStore, SpecificContent] with

    override
    def writeStore(
      store: SpecificStore,
      destination: UnsignedByte,
      content: SpecificContent
    ): SpecificStore =

      val appendedBlocks = content.supplementBlocks(store.blocks.length)
      val expandedBlocks = store.blocks.appendedAll(appendedBlocks)

      val modifiedBlocks = expandedBlocks.zipWithIndex.map { case (block, index) =>
        content.write(index, destination, block)
      }

      val contractedBlocks = trimmer.trimRight(modifiedBlocks)

      store.copy(blocks = contractedBlocks)
