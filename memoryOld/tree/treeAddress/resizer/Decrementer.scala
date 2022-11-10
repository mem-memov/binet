package net.mem_memov.binet.memoryOld.tree.treeAddress.resizer

import net.mem_memov.binet.memory.memory.general.UnsignedByte

trait Decrementer:

  def decrement(
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]]
