package net.mem_memov.binet.hexagon.general.factory

trait MakeDot[FACTORY, ADDRESS, DOT, ENTRY]:

  def f(
    address: ADDRESS,
    entry: ENTRY
  ): DOT

  extension (factory: FACTORY)

    def makeDot(
      address: ADDRESS,
      entry: ENTRY
    ): DOT =

      f(address, entry)
