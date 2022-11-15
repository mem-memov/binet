package net.mem_memov.binet.memory.general.address

trait ToContent[ADDRESS, CONTENT]:

  def addressToContent(
    address: ADDRESS
  ): CONTENT

  extension (address: ADDRESS)

    def toContent: CONTENT =

      addressToContent(address)
