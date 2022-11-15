package net.mem_memov.binet.memory.general.address

trait Length[ADDRESS]:

  def lengthOfAddress(
    address: ADDRESS
  ): Int

  extension (address: ADDRESS)

    def length: Int =

      lengthOfAddress(address)