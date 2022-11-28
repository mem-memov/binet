package net.mem_memov.binet.memory.general.factory

trait EmptyBlock[FACTORY, BLOCK]:

  private[EmptyBlock]
  def f(): BLOCK

  extension (factory: FACTORY)

    def emptyBlock(): BLOCK =

      f()
