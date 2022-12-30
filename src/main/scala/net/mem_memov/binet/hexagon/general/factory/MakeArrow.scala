package net.mem_memov.binet.hexagon.general.factory

trait MakeArrow[FACTORY, ARROW, ENTRY]:

  def f(
    entries: (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)
  ): ARROW

  extension (factory: FACTORY)

    def makeArrow(
      entries: (ENTRY, ENTRY, ENTRY, ENTRY, ENTRY, ENTRY)
    ): ARROW =

      f(entries)

