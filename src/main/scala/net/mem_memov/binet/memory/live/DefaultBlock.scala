package net.mem_memov.binet.memory.live

import net.mem_memov.binet.memory.*

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
  ): DefaultBlock =
    
    this.copy(space = space.updated(position.toInt, content))
