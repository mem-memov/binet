package net.mem_memov.binet.memory.general.factory

trait EmptyStock[FACTORY, STOCK]:

  private[EmptyStock]
  def f(): STOCK

  extension (factory: FACTORY)

    def emptyStock(): STOCK =

      f()
