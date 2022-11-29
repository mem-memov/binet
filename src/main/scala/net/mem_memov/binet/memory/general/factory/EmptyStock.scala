package net.mem_memov.binet.memory.general.factory

trait EmptyStock[FACTORY, STOCK]:

  def f(): STOCK

  extension (factory: FACTORY)

    def emptyStock(): STOCK =

      f()
