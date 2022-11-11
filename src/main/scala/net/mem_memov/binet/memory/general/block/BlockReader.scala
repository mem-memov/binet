package net.mem_memov.binet.memory.general.block

import net.mem_memov.binet.memory.general.UnsignedByte

trait BlockReader[BLOCK]:

  def readBlock(
    block: BLOCK,
    position: UnsignedByte
  ): UnsignedByte

  extension (block: BLOCK)

    def read(
      position: UnsignedByte
    ): UnsignedByte =

      readBlock(block, position)