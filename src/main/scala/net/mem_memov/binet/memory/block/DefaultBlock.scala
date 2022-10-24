package net.mem_memov.binet.memory.block

import net.mem_memov.binet.memory._

case class DefaultBlock(
  space: Vector[UnsignedByte]
) extends Block with WritableBlock with ReadableBlock:

  override
  def read(
    position: UnsignedByte
  ): UnsignedByte =
    space(position.toInt)

  override
  def write(
    position: UnsignedByte,
    content: UnsignedByte
  ): DefaultBlock =
    this.copy(space = space.updated(position.toInt, content))
