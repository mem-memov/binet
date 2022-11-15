package net.mem_memov.binet.memory.specific.address.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.formatter.*

class SpecificFormatter

object SpecificFormatter:

  given [
    FORMATTER : TrimmingFormatter
  ](using
    trimmer: FORMATTER
  ): PaddingFormatter[SpecificFormatter] with

    override
    def padBigIndices(
      formatter: SpecificFormatter,
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

  given TrimmingFormatter[SpecificFormatter] with

    override
    def trimBigIndices(
      formatter: SpecificFormatter,
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      val trimmedIndices = indices.dropWhile(_.atMinimum)

      if trimmedIndices.isEmpty then
        List(UnsignedByte.minimum)
      else
        trimmedIndices
