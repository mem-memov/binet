package net.mem_memov.binet.memory.general.block

import net.mem_memov.binet.memory.general.UnsignedByte

trait Write[BLOCK]:

  def f(
    block: BLOCK,
    position: UnsignedByte,
    content: UnsignedByte
  ): BLOCK

  extension (block: BLOCK)

    def write(
      position: UnsignedByte,
      content: UnsignedByte
    ): BLOCK =

      f(block, position, content)
