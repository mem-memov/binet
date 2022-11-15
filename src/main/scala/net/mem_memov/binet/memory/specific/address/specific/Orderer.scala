package net.mem_memov.binet.memory.specific.address.specific

import net.mem_memov.binet.memory.general.address.Indices
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.address.general.formatter.TrimBig
import net.mem_memov.binet.memory.specific.address.general.orderer.Compare

class Orderer

object Orderer:

  given [
    ADDRESS : Indices,
    FORMATTER : TrimBig
  ](using
    formatter: FORMATTER
  ): Compare[Orderer, ADDRESS] with

    override
    def compareAddresses(
      oderer: Orderer,
      left: ADDRESS,
      right: ADDRESS
    ): Int =

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
