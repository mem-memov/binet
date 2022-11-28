package net.mem_memov.binet.memory.general.block

trait IsEmpty[BLOCK]:

  private[IsEmpty]
  def f(
    block: BLOCK
  ): Boolean

  extension (block: BLOCK)

    def isEmpty: Boolean =

      f(block)