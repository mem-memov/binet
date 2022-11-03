package net.mem_memov.binet.memory.tree.treeAddress

import net.mem_memov.binet.memory.UnsignedByte

trait Formatter:

  def trimBig(
    indices: List[UnsignedByte]
  ): List[UnsignedByte]

  def padBig(
    target: Int,
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]