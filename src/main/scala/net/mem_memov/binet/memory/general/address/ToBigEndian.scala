package net.mem_memov.binet.memory.general.address

trait ToBigEndian[ADDRESS]:

  def f(address: ADDRESS): Array[Byte]

  extension (address: ADDRESS)

    def toBigEndian: Array[Byte] =

      f(address)
