package net.mem_memov.binet.memory.general.block

trait IsEmpty[BLOCK]:

  def isBlockEmpty(
    block: BLOCK
  ): Boolean

  extension (block: BLOCK)

    def isEmpty: Boolean =

      isBlockEmpty(block)