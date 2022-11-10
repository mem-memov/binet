package net.mem_memov.binet.memory.general.address

trait AddressComparabilityChecker[ADDRESS]:

  def canCompareWithAddress(
    address: ADDRESS,
    that: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def canCompare(
      that: ADDRESS
    ): Boolean =

      canCompareWithAddress(address, that)
