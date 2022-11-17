package net.mem_memov.binet.memory.general.address

trait ToString[ADDRESS]:

  def f(
    address: ADDRESS
  ): String

  extension (address: ADDRESS)

    def toString: String =

      f(address)