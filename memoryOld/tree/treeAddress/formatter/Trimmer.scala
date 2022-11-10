package net.mem_memov.binet.memoryOld.tree.treeAddress.formatter

import net.mem_memov.binet.memory.memory.general.UnsignedByte

trait Trimmer:

  def trimBig(
    indices: List[UnsignedByte]
  ): List[UnsignedByte]
