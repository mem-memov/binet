package net.mem_memov.binet.memory.tree.treeAddress.formatter.padder

import net.mem_memov.binet.memory.UnsignedByte
import net.mem_memov.binet.memory.tree.treeAddress.formatter.{Padder, Trimmer}

class PadderService(trimmer: Trimmer) extends Padder:

  def padBig(
    target: Int,
    indices: List[UnsignedByte]
  ): Either[String, List[UnsignedByte]] =

    if indices.length == target then
      Right(indices)
    else
      val trimmed = trimmer.trimBig(indices)
      if trimmed.length == target then
        Right(trimmed)
      else
        if trimmed.length > target then
          Left("Address not padded: already too long")
        else
          Right(
            List.fill(target - indices.length)(UnsignedByte.minimum) ++ indices
          )
