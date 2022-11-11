package net.mem_memov.binet.memory.general.content

import net.mem_memov.binet.memory.general.UnsignedByte

trait ContentWriter[CONTENT, BLOCK]:

  def writeContent(
    content: CONTENT,
    contentIndex: Integer,
    blockIndex: UnsignedByte,
    block: BLOCK
  ): BLOCK

  extension (content: CONTENT)

    def write(
      contentIndex: Integer,
      blockIndex: UnsignedByte,
      block: BLOCK
    ): BLOCK =

      writeContent(content, contentIndex, blockIndex, block)