package net.mem_memov.binet.memoryOld.tree.treeAddress.resizer

import net.mem_memov.binet.memory.memory.general.UnsignedByte

trait Incrementer:

  def increment(
    indices: List[UnsignedByte]
  ): List[UnsignedByte]
