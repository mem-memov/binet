package net.mem_memov.binet.memory.general.address

trait AddressGreaterThanOrEqualChecker[ADDRESS]:

  def isGreaterOrEqualToAddress(
    address: ADDRESS,
    that: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isGreaterOrEqual(
      that: ADDRESS
    ): Boolean =

      isGreaterOrEqualToAddress(address, that)
