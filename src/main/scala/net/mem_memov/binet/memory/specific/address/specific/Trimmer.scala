package net.mem_memov.binet.memory.specific.address.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.padder.PadBig
import net.mem_memov.binet.memory.specific.address.general.trimmer.TrimBig

class Trimmer

object Trimmer:

  given net_mem_memov_binet_memory_specific_address_specific_Trimmer_TrimBig: TrimBig[Trimmer] with

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
