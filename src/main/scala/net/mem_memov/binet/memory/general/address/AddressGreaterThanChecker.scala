package net.mem_memov.binet.memory.general.address

trait AddressGreaterThanChecker[ADDRESS]:

  def isGreaterThanAddress(
    address: ADDRESS,
    that: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isGreater(
      that: ADDRESS
    ): Boolean =

      isGreaterThanAddress(address, that)
