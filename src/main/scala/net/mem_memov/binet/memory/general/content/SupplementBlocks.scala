package net.mem_memov.binet.memory.general.content

trait SupplementBlocks[CONTENT, BLOCK]:

  def supplementContentBlocks(
    content: CONTENT,
    targetLength: Int
  ): Vector[BLOCK]

  extension (content: CONTENT)

    def supplementBlocks(
      targetLength: Int
    ): Vector[BLOCK] =

      supplementContentBlocks(content, targetLength)