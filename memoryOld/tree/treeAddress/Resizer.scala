package net.mem_memov.binet.memoryOld.tree.treeAddress

import net.mem_memov.binet.memory.memory.general.UnsignedByte

trait Resizer:

  def increment(
    indices: List[UnsignedByte]
  ): List[UnsignedByte]

  def decrement(
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]
