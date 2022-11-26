package net.mem_memov.binet.memory.specific.address.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.address.general.orderer.Compare
import net.mem_memov.binet.memory.specific.address.general.trimmer.TrimBig

class Orderer

object Orderer:

  given net_mem_memov_binet_memory_specific_address_specific_Orderer_Compare[
    TRIMMER
  ](using
    => TrimBig[TRIMMER]
  )(using
    trimmer: TRIMMER
  ): Compare[Orderer, Address] with

    override
    def f(
      oderer: Orderer,
      left: Address,
      right: Address
    ): Int =

      val trimmedLeft = trimmer.trimBig(left.parts)
      val trimmedRight = trimmer.trimBig(right.parts)
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
