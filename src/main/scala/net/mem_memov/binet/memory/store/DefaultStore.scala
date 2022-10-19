package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.{Address, Block, CompoundAddress, Store, UnsignedByte}

class DefaultStore(blocks: Vector[Block]) extends Store:

  override
  def write(
    destination: UnsignedByte,
    content: CompoundAddress
  ): Either[String, Store] =

    for {
      updatedBlocks <- content.zipIndices(blocks).map { case (part, block) => block.write(destination, part) }
    } yield Store(updatedBlocks)

  override
  def read(
    origin: UnsignedByte
  ): Address =

    val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
      case(parts, block) =>
        block.read(origin) :: parts
    }

    Address(parts.reverse)

  override
  def enlarge(): Store =
    Store(blocks.prepended(Block()))
