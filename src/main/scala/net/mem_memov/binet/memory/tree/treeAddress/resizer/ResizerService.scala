package net.mem_memov.binet.memory.tree.treeAddress.resizer

import net.mem_memov.binet.memory.UnsignedByte
import net.mem_memov.binet.memory.tree.treeAddress.Resizer

class ResizerService(
  incrementer: Incrementer,
  decrementer: Decrementer
) extends Resizer:

  def increment(
    indices: List[UnsignedByte]
  ): List[UnsignedByte] =
  
    incrementer.increment(indices)

  def decrement(
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]] =

    decrementer.decrement(indices)