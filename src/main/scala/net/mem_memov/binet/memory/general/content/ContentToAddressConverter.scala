package net.mem_memov.binet.memory.general.content

trait ContentToAddressConverter[CONTENT]:

  def contentToAddress[
    ADDRESS
  ](
    content: CONTENT
  ): ADDRESS

  extension (content: CONTENT)

    def toAddress[
      ADDRESS
    ]: ADDRESS =

      contentToAddress(content)
