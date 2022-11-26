package net.mem_memov.binet.memory.specific.address.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.padder.PadBig
import net.mem_memov.binet.memory.specific.address.general.trimmer.TrimBig

class Trimmer

object Trimmer:

  given TrimBig[Trimmer] with

    override
    def f(
      formatter: Trimmer,
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      val trimmedIndices = indices.dropWhile(_.atMinimum)

      if trimmedIndices.isEmpty then
        List(UnsignedByte.minimum)
      else
        trimmedIndices
