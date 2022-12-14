package net.mem_memov.binet.memory.general.content

trait ToAddress[CONTENT, ADDRESS]:

  def f(
    content: CONTENT
  ): ADDRESS

  extension (content: CONTENT)

    def toAddress: ADDRESS =

      f(content)
