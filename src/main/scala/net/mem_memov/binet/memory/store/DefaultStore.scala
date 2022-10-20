package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.{Address, Block, Store, UnsignedByte}

case class DefaultStore(
  blocks: Vector[Block]
) extends Store:

  override
  def write(
    destination: UnsignedByte,
    content: Address
  ): Either[String, Store] =

    if !content.hasLength(blocks.length) then
      Left("Destination not written: content has wrong number of indices")
    else
      val updatedBlocks = content.zipIndices(blocks).map { case (part, block) =>
        block.write(destination, part)
      }
      Right(this.copy(blocks = updatedBlocks))

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
      val prependedBlocks = (0 to minimumLength - blocks.length).map(_ => DefaultBlock.empty)
      this.copy(blocks = blocks.prependedAll(prependedBlocks))

  override
  def padBig(
    content: Address
  ): Either[String, Address] =
    content.padBig(blocks.length)

object DefaultStore:

  def empty(number: Int): Store =
    DefaultStore(Vector.fill[Block](number + 1)(DefaultBlock.empty))