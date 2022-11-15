package net.mem_memov.binet.memory.general.content

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general.block.BlockWriter

trait ContentWriter[CONTENT]:

  def writeContent[
    BLOCK
  ](
    content: CONTENT,
    contentIndex: Integer,
    blockIndex: UnsignedByte,
    block: BLOCK
  ): BLOCK

  extension (content: CONTENT)

    def write[
      BLOCK : BlockWriter
    ](
      contentIndex: Integer,
      blockIndex: UnsignedByte,
      block: BLOCK
    ): BLOCK =

      writeContent(content, contentIndex, blockIndex, block)