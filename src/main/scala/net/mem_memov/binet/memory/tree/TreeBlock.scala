package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*

case class TreeBlock(
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
  ): TreeBlock =
    
    this.copy(space = space.updated(position.toInt, content))
