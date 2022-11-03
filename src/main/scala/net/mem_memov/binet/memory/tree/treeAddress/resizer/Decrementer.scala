package net.mem_memov.binet.memory.tree.treeAddress.resizer

import net.mem_memov.binet.memory.UnsignedByte

trait Decrementer:

  def decrement(
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]
