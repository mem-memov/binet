package net.mem_memov.binet.memory.specific.address.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.trimmer.TrimBig
import net.mem_memov.binet.memory.specific.address.general.padder.PadBig

class Padder

object Padder:

  given net_mem_memov_binet_memory_specific_address_specific_Padder_PadBig[
    TRIMMER
  ](using
    TrimBig[TRIMMER]
  )(using
    trimmer: TRIMMER
  ): PadBig[Padder] with

    override
    def f(
      padder: Padder,
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


