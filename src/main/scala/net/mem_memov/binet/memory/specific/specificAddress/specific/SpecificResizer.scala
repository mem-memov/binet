package net.mem_memov.binet.memory.specific.specificAddress.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.specificAddress.general.resizer.IncrementingResizer
import net.mem_memov.binet.memory.specific.specificAddress.general.resizer.*

class SpecificResizer

object SpecificResizer:

  given DecrementingResizer[SpecificResizer] with

    override
    def decrementIndices(
      resizer: SpecificResizer,
      indices: List[UnsignedByte]
    ): Either[String, List[UnsignedByte]] =

      if indices.isEmpty then
        Left("Address not decremented: malformed")
      else
        val (accumulator, _, hasOverflow) = indices.reverse.foldLeft((List.empty[UnsignedByte], true, false)) {
          case ((accumulator, isStart, hasOverflow), index) =>
            if isStart then
              val (decrementedIndex, overflow) = minusOne(index)
              (decrementedIndex :: accumulator, false, overflow)
            else
              if hasOverflow then
                val (decrementedIndex, overflow) = minusOne(index)
                (decrementedIndex :: accumulator, false, overflow)
              else
                (index :: accumulator, false, false)
        }

        if hasOverflow then
          Left("Address not decremented: already at minimum")
        else
          Right(accumulator.reverse)

    def minusOne(x: UnsignedByte): (UnsignedByte, Boolean) =
      if x.atMinimum then (UnsignedByte.minimum, true) else (x.decrement, false)

  given IncrementingResizer[SpecificResizer] with

    override
    def incrementIndices(
      resizer: SpecificResizer,
      indices: List[UnsignedByte]
    ): List[UnsignedByte] =

      val (accumulator, _, hasOverflow) = indices.reverse.foldLeft((List.empty[UnsignedByte], true, false)) {
        case ((accumulator, isStart, hasOverflow), index) =>
          if isStart then
            val (incrementedIndex, overflow) = plusOne(index)
            (incrementedIndex :: accumulator, false, overflow)
          else if hasOverflow then
            val (incrementedIndex, overflow) = plusOne(index)
            (incrementedIndex :: accumulator, false, overflow)
          else
            (index :: accumulator, false, false)
      }

      if hasOverflow then
        UnsignedByte.minimum.increment :: accumulator.reverse
      else
        accumulator.reverse

    def plusOne(
      x: UnsignedByte
    ): (UnsignedByte, Boolean) =

      if x.atMaximum then
        (UnsignedByte.minimum, true)
      else
        (x.increment, false)
