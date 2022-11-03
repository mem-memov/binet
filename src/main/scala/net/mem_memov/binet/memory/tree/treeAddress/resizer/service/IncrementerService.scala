package net.mem_memov.binet.memory.tree.treeAddress.resizer.service

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeAddress.resizer.Incrementer

class IncrementerService extends Incrementer:

  override
  def increment(
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

    if hasOverflow then UnsignedByte.minimum.increment :: accumulator.reverse else accumulator.reverse

  def plusOne(
    x: UnsignedByte
  ): (UnsignedByte, Boolean) =

    if x.atMaximum then
      (UnsignedByte.minimum, true)
    else
      (x.increment, false)