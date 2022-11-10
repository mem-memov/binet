package net.mem_memov.binet.memory.general.address

trait AddressSerializer[ADDRESS]:

  def addressToString(
    address: ADDRESS
  ): String

  extension (address: ADDRESS)

    def toString: String =

      addressToString(address)