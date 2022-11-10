package net.mem_memov.binet.memoryOld.tree.treeFactory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.TreeBlock

trait BlockFactory:

  lazy val emptyBlock: Block

object BlockFactory:

  def apply(): BlockFactory =

    new BlockFactory:

      override lazy val emptyBlock: Block =
        TreeBlock(
          Vector.fill(UnsignedByte.maximum.toInt + 1)(UnsignedByte.minimum)
        )



