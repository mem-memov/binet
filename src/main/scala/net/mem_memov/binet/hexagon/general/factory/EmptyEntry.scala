package net.mem_memov.binet.hexagon.general.factory

trait EmptyEntry[FACTORY, ENTRY]:

  def f(): ENTRY

  extension (factory: FACTORY)

    def emptyEntry(): ENTRY =

      f()
