package net.mem_memov.binet.memory.specific.address.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.address.general.formatter.TrimBig
import net.mem_memov.binet.memory.specific.address.general.orderer.Compare

class Orderer

object Orderer:

  given [
    FORMATTER
  ](using
    TrimBig[FORMATTER]
  )(using
    formatter: FORMATTER
  ): Compare[Orderer, Address] with

    override
    def f(
      oderer: Orderer,
      left: Address,
      right: Address
    ): Int =

      val trimmedLeft = formatter.trimBig(left.parts)
      val trimmedRight = formatter.trimBig(right.parts)
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
