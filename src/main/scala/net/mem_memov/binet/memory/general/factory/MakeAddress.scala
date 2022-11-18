package net.mem_memov.binet.memory.general.factory

import net.mem_memov.binet.memory.general

trait MakeAddress[FACTORY, ADDRESS]:

  def f(
    indices: List[general.UnsignedByte]
  ): ADDRESS

  extension (factory: FACTORY)

    def zeroAddress(
      indices: List[general.UnsignedByte]
    ): ADDRESS =

      f(indices)
