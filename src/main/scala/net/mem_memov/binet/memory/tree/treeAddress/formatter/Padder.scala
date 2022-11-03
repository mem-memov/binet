package net.mem_memov.binet.memory.tree.treeAddress.formatter

import net.mem_memov.binet.memory.UnsignedByte

trait Padder:

  def padBig(
    target: Int,
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]
