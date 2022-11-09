package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeStore.Trimmer

import scala.annotation.tailrec

case class TreeStore(
  blocks: Vector[Block]
)

object TreeStore:

  given (using
    trimmer: Trimmer,
    addressFactory: AddressFactory,
    blockFactory: BlockFactory
  ): Store[TreeStore] with

    override
    def writeStore(
      store: TreeStore,
      destination: UnsignedByte,
      content: Content
    ): TreeStore =

      val appendedBlocks = content.supplementBlocks(store.blocks.length)
      val expandedBlocks = store.blocks.appendedAll(appendedBlocks)

      val modifiedBlocks = expandedBlocks.zipWithIndex.map { case (block, index) =>
        content.write(index, destination, block)
      }

      val contractedBlocks = trimmer.trimRight(modifiedBlocks)

      store.copy(blocks = contractedBlocks)

    override
    def readStore(
      store: TreeStore,
      origin: UnsignedByte
    ): Address =

      val parts = store.blocks.foldLeft(List.empty[UnsignedByte]) {
        case(parts, block) =>
          block.read(origin) :: parts
      }

      addressFactory.makeAddress(parts.reverse)

