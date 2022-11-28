package net.mem_memov.binet.memory.general.factory

trait EmptyStore[FACTORY, STORE]:

  private[EmptyStore]
  def f(): STORE
  
  extension (factory: FACTORY)

    def emptyStore(): STORE =

      f()