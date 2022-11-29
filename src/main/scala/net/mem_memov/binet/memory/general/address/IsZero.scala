package net.mem_memov.binet.memory.general.address

trait IsZero[ADDRESS]:

  def f(
    address: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isZero: Boolean =

      f(address)
