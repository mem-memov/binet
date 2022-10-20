package net.mem_memov.binet.memory.block

import net.mem_memov.binet.memory.{Block, UnsignedByte}

case class DefaultBlock(
  space: Vector[UnsignedByte]
) extends Block:

  override
  def read(
    position: UnsignedByte
  ): UnsignedByte =
    space(position.toInt)

  override
  def write(
    position: UnsignedByte,
    content: UnsignedByte
  ): Block =
    this.copy(space = space.updated(position.toInt, content))

object DefaultBlock:

  val empty: Block =
    DefaultBlock(
      Vector.fill(UnsignedByte.maximum.toInt + 1)(UnsignedByte.minimum)
    )