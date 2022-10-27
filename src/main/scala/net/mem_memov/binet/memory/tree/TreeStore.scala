package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._

case class TreeStore(
  blocks: Vector[Block]
)(using
  addressFactory: AddressFactory,
  blockFactory: BlockFactory
) extends Store:

  override
  def write(
    destination: UnsignedByte,
    content: Address
  ): Either[String, TreeStore] =

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

    addressFactory.makeAddress(parts.reverse)

  override
  def expand(
    minimumLength: Int
  ): TreeStore =

    if blocks.length >= minimumLength then
      this
    else
      val prependedBlocks = (0 until minimumLength - blocks.length).map(_ => blockFactory.emptyBlock)
      this.copy(blocks = blocks.prependedAll(prependedBlocks))
