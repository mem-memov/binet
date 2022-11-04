package net.mem_memov.binet.memory.tree.treeAddress.orderer

import net.mem_memov.binet.memory.Address
import net.mem_memov.binet.memory.tree.treeAddress.{Formatter, Orderer}

class OrdererService(
  formatter: Formatter
) extends Orderer:

  def compare(left: Address, right: Address): Int =

    val trimmedLeft = formatter.trimBig(left.indices)
    val trimmedRight = formatter.trimBig(right.indices)
    if trimmedLeft.length != trimmedRight.length then
      trimmedLeft.length - trimmedRight.length
    else
      val difference = trimmedLeft.zip(trimmedRight)
        .dropWhile { case (thisIndex, thatIndex) =>
          thisIndex == thatIndex
        }
      if difference.isEmpty then
        0
      else
        val (leftIndex, rightIndex) = difference.head
        if leftIndex > rightIndex then 1 else -1