package net.mem_memov.binet.memory.general.address

trait AddressZeroChecker[ADDRESS]:

  def isAddressZero(
    address: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isZero: Boolean =

      isAddressZero(address)
