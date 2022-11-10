package net.mem_memov.binet.memory.general.address

trait AddressLessThanOrEqualChecker[ADDRESS]:

  def isLessOrEqualToAddress(
    address: ADDRESS,
    that: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isLessOrEqual(
      that: ADDRESS
    ): Boolean =

      isLessOrEqualToAddress(address, that)
