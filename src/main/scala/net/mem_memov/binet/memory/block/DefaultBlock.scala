package net.mem_memov.binet.memory.block

import net.mem_memov.binet.memory.{Block, UnsignedByte}

class DefaultBlock(space: Vector[UnsignedByte]) extends Block:

  def read(position: UnsignedByte): UnsignedByte =
    space(position.toInt)

  def write(position: UnsignedByte, content: UnsignedByte): Block =
    Block(space.updated(position.toInt, content))
