package net.mem_memov.binet.hexagon.general.factory

trait MakeArrow[FACTORY, ADDRESS, ARROW, ENTRY]:

  def f(
    address: ADDRESS,
    entry: ENTRY
  ): ARROW

  extension (factory: FACTORY)

    def makeArrow(
      address: ADDRESS,
      entry: ENTRY
    ): ARROW =

      f(address, entry)

