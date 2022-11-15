package net.mem_memov.binet.memory.general.address

trait AddressToContentConverter[ADDRESS, CONTENT]:

  def addressToContent(
    address: ADDRESS
  ): CONTENT

  extension (address: ADDRESS)

    def toContent: CONTENT =

      addressToContent(address)
