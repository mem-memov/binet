package net.mem_memov.binet.memory.general.address

trait AddressEmptyChecker[ADDRESS]:

  def isAddressEmpty(
    address: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isEmpty: Boolean =

      isAddressEmpty(address)