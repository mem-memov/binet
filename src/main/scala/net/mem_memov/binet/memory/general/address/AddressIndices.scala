package net.mem_memov.binet.memory.general.address

import net.mem_memov.binet.memory.general.UnsignedByte

trait AddressIndices[ADDRESS]:

  def indicesOfAddress(
    address: ADDRESS
  ): List[UnsignedByte]

  extension (address: ADDRESS)

    def indices: List[UnsignedByte] =

      indicesOfAddress(address)