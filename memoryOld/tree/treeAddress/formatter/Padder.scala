package net.mem_memov.binet.memoryOld.tree.treeAddress.formatter

import net.mem_memov.binet.memory.memory.general.UnsignedByte

trait Padder:

  def padBig(
    target: Int,
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]
