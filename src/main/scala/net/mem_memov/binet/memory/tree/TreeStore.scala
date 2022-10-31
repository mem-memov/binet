package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*

import scala.annotation.tailrec

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
  def write(
    destination: UnsignedByte,
    content: Vector[UnsignedByte]
  ): Store =

    val expandedBlocks = if  blocks.length < content.length then
      val prependedBlocks = (0 to content.length - blocks.length).map(_ => blockFactory.emptyBlock)
      blocks.prependedAll(prependedBlocks)
    else
      blocks

    val expandedContent = if content.length < blocks.length then
      val prependedBlocks = (0 to blocks.length - content.length).map(_ => UnsignedByte.minimum).toVector
      content.prependedAll(prependedBlocks)
    else
      content

    val modifiedBlocks = expandedContent.zip(expandedBlocks).map { case (part, block) =>
      block.write(destination, part)
    }

    this.copy(blocks = modifiedBlocks)

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

  override
  def foreachSlice(
    f: Array[Byte] => Unit
  ): Unit =

    def d(
      origin: UnsignedByte,
      f: Array[Byte] => Unit
    ): Unit =
      val parts = blocks.foldLeft(List.empty[UnsignedByte]) { // TODO: merge repetitive code
        case(parts, block) =>
          block.read(origin) :: parts
      }

      val slice = parts.reverse.map(_.value).toArray
      f(slice)

    @tailrec
    def t(
      origin: UnsignedByte,
      f: Array[Byte] => Unit
    ): Unit =

      if origin < UnsignedByte.maximum then
        d(origin, f)
        t(origin.increment, f)
      else
        d(origin, f)

    t(UnsignedByte.minimum, f)