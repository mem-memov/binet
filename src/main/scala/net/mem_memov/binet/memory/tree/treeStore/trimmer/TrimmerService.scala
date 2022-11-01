package net.mem_memov.binet.memory.tree.treeStore.trimmer

import net.mem_memov.binet.memory.Block
import net.mem_memov.binet.memory.tree.treeStore.Trimmer

class TrimmerService extends Trimmer:

  override
  def trimRight(
    blocks: Vector[Block]
  ): Vector[Block] =

    if blocks.isEmpty then
      blocks
    else
      if blocks.last.isEmpty then
        trimRight(blocks.dropRight(1))
      else
        blocks
