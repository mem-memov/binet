package net.mem_memov.binet.memory.general.address

trait Decrement[ADDRESS]:

  def decrementAddress(
    address: ADDRESS
  ): Either[String, ADDRESS]

  extension (address: ADDRESS)

    def decrement: Either[String, ADDRESS] =

      decrementAddress(address)
