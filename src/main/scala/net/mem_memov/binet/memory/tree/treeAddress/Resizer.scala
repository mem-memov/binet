package net.mem_memov.binet.memory.tree.treeAddress

import net.mem_memov.binet.memory.UnsignedByte

trait Resizer:

  def increment(
    indices: List[UnsignedByte]
  ): List[UnsignedByte]

  def decrement(
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]
