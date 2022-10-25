package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.factory.defaultFactory._
import net.mem_memov.binet.memory._

case class DefaultStore(
  blocks: Vector[Block]
)(using
  addressFactory: AddressFactory,
  blockFactory: BlockFactory
) extends Store:

  override
  def write(
    destination: UnsignedByte,
    content: Address
  ): Either[String, Store] =

    DefaultStore.write(
      destination,
      content,
      blocks,
      updateWithBlocks
    )

  override
  def read(
    origin: UnsignedByte
  ): Address =

    DefaultStore.read(
      origin,
      blocks,
      addressFactory
    )

  override
  def expand(
    minimumLength: Int
  ): Store =

    DefaultStore.expand(
      minimumLength,
      blocks,
      blockFactory,
      updateWithBlocks,
      this
    )

  def updateWithBlocks(
    updatedBlocks: Vector[Block]
  ): Store =

    this.copy(blocks = updatedBlocks)

object DefaultStore:

  def write(
    destination: UnsignedByte,
    content: Address,
    blocks: Vector[Block],
    updateWithBlocks: Vector[Block] => Store
  ): Either[String, Store] =

    for {
      pairs <- content.zipIndices(blocks)
      updatedBlocks <- Right(
        pairs.map { case (part, block) =>
          block.write(destination, part)
        }
      )
    } yield updateWithBlocks(updatedBlocks)

  def read(
    origin: UnsignedByte,
    blocks: Vector[Block],
    addressFactory: AddressFactory
  ): Address =

    val parts = blocks.foldLeft(List.empty[UnsignedByte]) {
      case(parts, block) =>
        block.read(origin) :: parts
    }

    addressFactory.makeAddress(parts.reverse)

  def expand(
    minimumLength: Int,
    blocks: Vector[Block],
    blockFactory: BlockFactory,
    updateWithBlocks: Vector[Block] => Store,
    originalStore: Store
  ): Store =

    if blocks.length >= minimumLength then
      originalStore
    else
      val prependedBlocks = (0 until minimumLength - blocks.length).map(_ => blockFactory.emptyBlock)
      updateWithBlocks(blocks.prependedAll(prependedBlocks))