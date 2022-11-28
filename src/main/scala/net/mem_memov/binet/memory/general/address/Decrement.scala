package net.mem_memov.binet.memory.general.address

trait Decrement[ADDRESS]:

  private[Decrement]
  def f(
    address: ADDRESS
  ): Either[String, ADDRESS]

  extension (address: ADDRESS)

    def decrement: Either[String, ADDRESS] =

      f(address)
