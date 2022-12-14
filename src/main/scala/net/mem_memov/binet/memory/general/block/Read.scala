package net.mem_memov.binet.memory.general.block

import net.mem_memov.binet.memory.general.UnsignedByte

trait Read[BLOCK]:

  def f(
    block: BLOCK,
    position: UnsignedByte
  ): UnsignedByte

  extension (block: BLOCK)

    def read(
      position: UnsignedByte
    ): UnsignedByte =

      f(block, position)