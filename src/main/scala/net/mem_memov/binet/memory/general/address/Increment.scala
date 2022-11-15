package net.mem_memov.binet.memory.general.address

trait Increment[ADDRESS]:

  def incrementAddress(
    address: ADDRESS
  ): ADDRESS

  extension (address: ADDRESS)

    def increment: ADDRESS =

      incrementAddress(address)
