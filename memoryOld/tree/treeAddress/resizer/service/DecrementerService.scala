package net.mem_memov.binet.memoryOld.tree.treeAddress.resizer.service

import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.treeAddress.resizer.Decrementer

class DecrementerService extends Decrementer:

  def decrement(
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