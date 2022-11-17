package net.mem_memov.binet.memory.general.address

import net.mem_memov.binet.memory.general.UnsignedByte

trait Indices[ADDRESS]:

  def f(
    address: ADDRESS
  ): List[UnsignedByte]

  extension (address: ADDRESS)

    def indices: List[UnsignedByte] =

      f(address)