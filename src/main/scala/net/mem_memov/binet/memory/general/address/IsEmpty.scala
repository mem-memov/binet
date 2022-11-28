package net.mem_memov.binet.memory.general.address

trait IsEmpty[ADDRESS]:

  private[IsEmpty]
  def f(
    address: ADDRESS
  ): Boolean

  extension (address: ADDRESS)

    def isEmpty: Boolean =

      f(address)