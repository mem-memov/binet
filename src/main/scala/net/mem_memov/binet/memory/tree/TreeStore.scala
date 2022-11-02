package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeStore.Trimmer

import scala.annotation.tailrec

case class TreeStore(
  blocks: Vector[Block],
  trimmer: Trimmer
)(using
  addressFactory: AddressFactory,
  blockFactory: BlockFactory
) extends Store:

  def write(
    destination: UnsignedByte,
    content: Content
  ): TreeStore =

    val appendedBlocks = content.supplementBlocks(blocks.length)
    val expandedBlocks = blocks.appendedAll(appendedBlocks)

    val modifiedBlocks = expandedBlocks.zipWithIndex.map { case (block, index) =>
      content.write(index, destination, block)
    }

    val contractedBlocks = trimmer.trimRight(modifiedBlocks)

    this.copy(blocks = contractedBlocks)

  override
  def read(
    origin: UnsignedByte
  ): Address =

    val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
      case(parts, block) =>
        block.read(origin) :: parts
    }

    addressFactory.makeAddress(parts.reverse)

