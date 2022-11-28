package net.mem_memov.binet.memory.general.factory

import net.mem_memov.binet.memory.general

trait MakeAddress[FACTORY, ADDRESS]:

  private[MakeAddress]
  def f(
    indices: List[general.UnsignedByte]
  ): ADDRESS

  extension (factory: FACTORY)

    def makeAddress(
      indices: List[general.UnsignedByte]
    ): ADDRESS =

      f(indices)
