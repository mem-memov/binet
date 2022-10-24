package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.factory.defaultFactory.BlockFactory
import net.mem_memov.binet.memory._

case class DefaultStore(
  blocks: Vector[Block]
)(using
  blockFactory: BlockFactory
) extends Store:

  override
  def write(
    destination: UnsignedByte,
    content: Address
  ): Either[String, Store] =

    for {
      pairs <- content.zipIndices(blocks)
      updatedBlocks <- Right(
        pairs.map { case (part, block) =>
          block.write(destination, part)
        }
      )
    } yield this.copy(blocks = updatedBlocks)

  override
  def read(
    origin: UnsignedByte
  ): Address =

    val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
      case(parts, block) =>
        block.read(origin) :: parts
    }

    DefaultAddress(parts.reverse)

  override
  def expand(
    minimumLength: Int
  ): Store =

    if blocks.length >= minimumLength then
      this
    else
      val prependedBlocks = (0 to minimumLength - blocks.length).map(_ => blockFactory.emptyBlock)
      this.copy(blocks = blocks.prependedAll(prependedBlocks))
