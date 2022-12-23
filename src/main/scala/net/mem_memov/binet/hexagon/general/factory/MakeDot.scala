package net.mem_memov.binet.hexagon.general.factory

trait MakeDot[FACTORY, DOT, ENTRY]:

  def f(
    entries: (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)
  ): DOT

  extension (factory: FACTORY)

    def makeDot(
      entries: (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)
    ): DOT =

      f(entries)
