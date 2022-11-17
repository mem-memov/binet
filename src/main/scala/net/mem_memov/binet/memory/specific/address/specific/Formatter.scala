package net.mem_memov.binet.memory.specific.address.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.formatter.*

class Formatter

object Formatter:

  given [
    FORMATTER : TrimBig
  ](using
    trimmer: FORMATTER
  ): PadBig[Formatter] with

    override
    def f(
      formatter: Formatter,
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

  given TrimBig[Formatter] with

    override
    def f(
      formatter: Formatter,
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      val trimmedIndices = indices.dropWhile(_.atMinimum)

      if trimmedIndices.isEmpty then
        List(UnsignedByte.minimum)
      else
        trimmedIndices
