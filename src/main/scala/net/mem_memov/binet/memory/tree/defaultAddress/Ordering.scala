package net.mem_memov.binet.memory.address.defaultAddress

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.DefaultAddress

trait Ordering:

  def compare(left: Address, right: Address): Int

object Ordering:

  def apply(): Ordering = new Ordering:

    def compare(left: Address, right: Address): Int =

      val trimmedLeft = left.trimBig
      val trimmedRight = right.trimBig
      if trimmedLeft.length != trimmedRight.length then
        trimmedLeft.length - trimmedRight.length
      else
        val difference = trimmedLeft.indices.zip(trimmedRight.indices)
          .dropWhile { case (thisIndex, thatIndex) =>
            thisIndex == thatIndex
          }
        if difference.isEmpty then
          0
        else
          val (leftIndex, rightIndex) = difference.head
          if leftIndex > rightIndex then 1 else -1
