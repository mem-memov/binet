package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.block.DefaultBlock

trait BlockFactory:

  def makeBlock(space: Vector[UnsignedByte]): Block

object BlockFactory:

  def apply(): BlockFactory = (space: Vector[UnsignedByte]) => DefaultBlock(space)