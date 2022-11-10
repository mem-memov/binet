package net.mem_memov.binet.memoryOld.tree.treeAddress.formatter

import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.treeAddress.Formatter

class FormatterService(
  padder: Padder,
  trimmer: Trimmer
) extends Formatter:

  def trimBig(
    indices: List[UnsignedByte]
  ): List[UnsignedByte] =

    trimmer.trimBig(indices)

  def padBig(
    target: Int,
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]] =

    padder.padBig(target, indices)
