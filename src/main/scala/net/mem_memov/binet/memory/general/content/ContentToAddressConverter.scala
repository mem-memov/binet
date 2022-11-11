package net.mem_memov.binet.memory.general.content

trait ContentToAddressConverter[CONTENT, ADDRESS]:

  def contentToAddress(
    content: CONTENT
  ): ADDRESS

  extension (content: CONTENT)

    def toAddress: ADDRESS =

      contentToAddress(content)
