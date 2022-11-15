package net.mem_memov.binet.memory.general.address

trait AddressToContentConverter[ADDRESS]:

  def addressToContent[
    CONTENT
  ](
    address: ADDRESS
  ): CONTENT

  extension (address: ADDRESS)

    def toContent[
      CONTENT
    ]: CONTENT =

      addressToContent(address)
