package net.mem_memov.binet.memory.general.address

trait Increment[ADDRESS]:

  def f(
    address: ADDRESS
  ): ADDRESS

  extension (address: ADDRESS)

    def increment: ADDRESS =

      f(address)
