package net.mem_memov.binet.memory.general.content

trait ContentBlockSupplementer[CONTENT]:

  def supplementContentBlocks[
    BLOCK
  ](
    content: CONTENT,
    targetLength: Int
  ): Vector[BLOCK]

  extension (content: CONTENT)

    def supplementBlocks[
      BLOCK
    ](
      targetLength: Int
    ): Vector[BLOCK] =

      supplementContentBlocks(content, targetLength)