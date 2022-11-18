package net.mem_memov.binet.memory.general.factory

trait ZeroAddress[FACTORY, ADDRESS]:

  def f(): ADDRESS

  extension (factory: FACTORY)

    def zeroAddress(): ADDRESS =

      f()
