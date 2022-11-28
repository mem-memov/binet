package net.mem_memov.binet.memory.general.factory

trait EmptyElement[FACTORY, ELEMENT]:

  private[EmptyElement]
  def f(): ELEMENT

  extension (factory: FACTORY)

    def emptyElement(): ELEMENT =

      f()
