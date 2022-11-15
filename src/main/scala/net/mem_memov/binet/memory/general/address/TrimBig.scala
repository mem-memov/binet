package net.mem_memov.binet.memory.general.address

trait TrimBig[ADDRESS]:

  def trimBigAddress(
    address: ADDRESS
  ): ADDRESS

  extension (address: ADDRESS)

    def trimBig: ADDRESS =

      trimBigAddress(address)