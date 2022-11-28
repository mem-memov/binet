package net.mem_memov.binet.memory.general.content

trait SupplementBlocks[CONTENT, BLOCK]:

  private[SupplementBlocks]
  def f(
    content: CONTENT,
    targetLength: Int
  ): Vector[BLOCK]

  extension (content: CONTENT)

    def supplementBlocks(
      targetLength: Int
    ): Vector[BLOCK] =

      f(content, targetLength)