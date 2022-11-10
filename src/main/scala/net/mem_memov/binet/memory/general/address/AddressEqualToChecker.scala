package net.mem_memov.binet.memory.general.address

trait AddressEqualToChecker[ADDRESS]:

  def isEqualToAddress(
    address: ADDRESS,
    that: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isEqual(
      that: ADDRESS
    ): Boolean =

      isEqualToAddress(address, that)