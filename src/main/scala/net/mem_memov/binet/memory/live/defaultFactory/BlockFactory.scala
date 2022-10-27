package net.mem_memov.binet.memory.live.defaultFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.live.DefaultBlock

trait BlockFactory:

  lazy val emptyBlock: Block

object BlockFactory:

  def apply(): BlockFactory =

    new BlockFactory:

      override lazy val emptyBlock: Block =
        DefaultBlock(
          Vector.fill(UnsignedByte.maximum.toInt + 1)(UnsignedByte.minimum)
        )



