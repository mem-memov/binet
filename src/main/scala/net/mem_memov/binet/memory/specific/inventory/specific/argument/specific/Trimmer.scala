package net.mem_memov.binet.memory.specific.inventory.specific.argument.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.trimmer.Trim
import net.mem_memov.binet.memory.general.address.{IsEmpty, TrimBig}

class Trimmer

object Trimmer:

  given [ADDRESS, FACTORY](using
    IsEmpty[ADDRESS],
    TrimBig[ADDRESS],
    general.factory.ZeroAddress[FACTORY, ADDRESS]
  )(using
    factory: FACTORY
  ): Trim[Trimmer, ADDRESS] with

    override
    def f(
      trimmer: Trimmer,
      address: ADDRESS
    ): ADDRESS =

      if address.isEmpty then factory.zeroAddress() else address.trimBig
