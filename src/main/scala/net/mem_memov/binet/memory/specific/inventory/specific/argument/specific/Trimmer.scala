package net.mem_memov.binet.memory.specific.inventory.specific.argument.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.inventory.specific.argument.general.trimmer.Trim
import net.mem_memov.binet.memory.general.address.{IsEmpty, TrimBig}

class Trimmer

object Trimmer:

  given net_mem_memov_binet_memory_specific_inventory_specific_argument_specific_Trimmer: Trimmer = new Trimmer

  given [ADDRESS, FACTORY](using
    => IsEmpty[ADDRESS],
    => TrimBig[ADDRESS],
    => general.factory.ZeroAddress[FACTORY, ADDRESS]
  )(using
    factory: FACTORY
  ): Trim[Trimmer, ADDRESS] with

    override
    def f(
      address: ADDRESS
    ): ADDRESS =

      if address.isEmpty then factory.zeroAddress() else address.trimBig
