package net.mem_memov.binet.memory.general.address

trait ToContent[ADDRESS, CONTENT]:

  def f(
    address: ADDRESS
  ): CONTENT

  extension (address: ADDRESS)

    def toContent: CONTENT =

      f(address)
