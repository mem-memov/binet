package net.mem_memov.binet.memoryOld.tree.treeStore

import net.mem_memov.binet.memory.Block

trait Trimmer:

  def trimRight(
    blocks: Vector[Block]
  ): Vector[Block]