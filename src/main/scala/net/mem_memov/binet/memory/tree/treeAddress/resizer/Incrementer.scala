package net.mem_memov.binet.memory.tree.treeAddress.resizer

import net.mem_memov.binet.memory.UnsignedByte

trait Incrementer:

  def increment(
    indices: List[UnsignedByte]
  ): List[UnsignedByte]
