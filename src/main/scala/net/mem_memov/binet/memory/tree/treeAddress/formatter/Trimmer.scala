package net.mem_memov.binet.memory.tree.treeAddress.formatter

import net.mem_memov.binet.memory.UnsignedByte

trait Trimmer:

  def trimBig(
    indices: List[UnsignedByte]
  ): List[UnsignedByte]
