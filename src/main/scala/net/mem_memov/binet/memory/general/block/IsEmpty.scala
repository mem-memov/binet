package net.mem_memov.binet.memory.general.block

trait IsEmpty[BLOCK]:

  def f(
    block: BLOCK
  ): Boolean

  extension (block: BLOCK)

    def isEmpty: Boolean =

      f(block)