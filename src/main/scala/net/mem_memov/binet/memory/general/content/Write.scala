package net.mem_memov.binet.memory.general.content

import net.mem_memov.binet.memory.general.UnsignedByte

trait Write[CONTENT, BLOCK]:

  def f(
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

      f(content, contentIndex, blockIndex, block)