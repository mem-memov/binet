package net.mem_memov.binet.memory.general.address

trait AddressIncrementer[ADDRESS]:

  def incrementAddress(
    address: ADDRESS
  ): ADDRESS

  extension (address: ADDRESS)

    def increment: ADDRESS =

      incrementAddress(address)
