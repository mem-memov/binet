package net.mem_memov.binet.memory.general.address

trait AddressToPathConverter[ADDRESS]:

  def addressToPath[
    PATH
  ](
    address: ADDRESS
  ): PATH

  extension (address: ADDRESS)

    def toPath[
      PATH
    ]: PATH =

      addressToPath(address)