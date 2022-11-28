package net.mem_memov.binet.memory.general.address

trait TrimBig[ADDRESS]:

  private[TrimBig]
  def f(
    address: ADDRESS
  ): ADDRESS

  extension (address: ADDRESS)

    def trimBig: ADDRESS =

      f(address)