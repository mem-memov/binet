package net.mem_memov.binet.memoryOld.tree.treeAddress.formatter.trimmer

import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.treeAddress.formatter.Trimmer

class TrimmerService extends Trimmer:

  def trimBig(
    indices: List[UnsignedByte]
  ): List[UnsignedByte] =

    val trimmedIndices = indices.dropWhile(_.atMinimum)

    if trimmedIndices.isEmpty then
      List(UnsignedByte.minimum)
    else
      trimmedIndices