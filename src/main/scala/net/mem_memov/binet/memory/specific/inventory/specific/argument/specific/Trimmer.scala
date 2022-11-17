package net.mem_memov.binet.memory.specific.inventory.specific.argument.specific

import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.trimmer.Trim
import net.mem_memov.binet.memory.general.address.{IsEmpty, TrimBig}

class Trimmer

object Trimmer:

  given (using
    IsEmpty[Address],
    TrimBig[Address]
  ): Trim[Trimmer, Address] with

    override
    def f(
      trimmer: Trimmer,
      address: Address
    ): Address =

      if address.isEmpty then Address.zeroAddress else address.trimBig
