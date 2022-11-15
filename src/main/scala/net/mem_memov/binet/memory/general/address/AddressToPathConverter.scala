package net.mem_memov.binet.memory.general.address

trait AddressToPathConverter[ADDRESS, PATH]:

  def addressToPath(
    address: ADDRESS
  ): PATH

  extension (address: ADDRESS)

    def toPath: PATH =

      addressToPath(address)