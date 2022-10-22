package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.block.DefaultBlock

trait BlockFactory:

  def makeEmptyBlock(): Block

object BlockFactory:

  val cachedFactory: Option[BlockFactory] = None

  def apply(): BlockFactory = cachedFactory.getOrElse {
    
    new BlockFactory:
      
      override def makeEmptyBlock(): Block =
        DefaultBlock(
          Vector.fill(UnsignedByte.maximum.toInt + 1)(UnsignedByte.minimum)
        )
  }


