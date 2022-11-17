package net.mem_memov.binet.memory.general.address

trait Length[ADDRESS]:

  def f(
    address: ADDRESS
  ): Int

  extension (address: ADDRESS)

    def length: Int =

      f(address)