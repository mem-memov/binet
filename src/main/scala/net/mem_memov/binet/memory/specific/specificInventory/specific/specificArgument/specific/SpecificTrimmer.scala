package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.trimmer.Trim
import net.mem_memov.binet.memory.general.address.{IsEmpty, TrimBig}

class SpecificTrimmer

object SpecificTrimmer:

  given (using
    IsEmpty[Address],
    TrimBig[Address]
  ): Trim[SpecificTrimmer, Address] with

    override
    def trimAddress(
      trimmer: SpecificTrimmer,
      address: Address
    ): Address =

      if address.isEmpty then Address.zeroAddress else address.trimBig
