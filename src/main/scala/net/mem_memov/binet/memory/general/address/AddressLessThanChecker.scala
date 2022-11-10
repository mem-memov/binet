package net.mem_memov.binet.memory.general.address

trait AddressLessThanChecker[ADDRESS]:

  def isLessThanAddress(
    address: ADDRESS,
    that: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isLess(
      that: ADDRESS
    ): Boolean =

      isLessThanAddress(address, that)