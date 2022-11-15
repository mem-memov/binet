package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.specific

import net.mem_memov.binet.memory.specific.SpecificAddress
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.trimmer.Trim
import net.mem_memov.binet.memory.general.address.{IsEmpty, TrimBig}

class SpecificTrimmer

object SpecificTrimmer:

  given (using
    IsEmpty[SpecificAddress],
    TrimBig[SpecificAddress]
  ): Trim[SpecificTrimmer, SpecificAddress] with

    override
    def trimAddress(
      trimmer: SpecificTrimmer,
      address: SpecificAddress
    ): SpecificAddress =

      if address.isEmpty then SpecificAddress.zeroAddress else address.trimBig
