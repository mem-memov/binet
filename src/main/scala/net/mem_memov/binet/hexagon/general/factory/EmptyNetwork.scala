package net.mem_memov.binet.hexagon.general.factory

trait EmptyNetwork[FACTORY, NETWORK]:

  def f(): NETWORK

  extension (factory: FACTORY)

    def emptyNetwork: NETWORK =

      f()
