package net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.specific

import net.mem_memov.binet.memory.specific.SpecificAddress
import net.mem_memov.binet.memory.specific.specificInventory.specific.specificArgument.general.trimmer.Trim
import net.mem_memov.binet.memory.general.address.{AddressEmptyChecker, AddressTrimmer}

class SpecificTrimmer

object SpecificTrimmer:

  given Trim[SpecificTrimmer] with

    override
    def trimAddress[
      ADDRESS : AddressEmptyChecker : AddressTrimmer
    ](
      trimmer: SpecificTrimmer,
      address: SpecificAddress
    ): SpecificAddress =

      if address.isEmpty then SpecificAddress.zeroAddress else address.trimBig
