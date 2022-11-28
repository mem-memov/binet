package net.mem_memov.binet.memory.general.address

trait ToPath[ADDRESS, PATH]:

  private[ToPath]
  def f(
    address: ADDRESS
  ): PATH

  extension (address: ADDRESS)

    def toPath: PATH =

      f(address)