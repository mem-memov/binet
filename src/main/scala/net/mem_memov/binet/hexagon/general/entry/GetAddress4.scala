package net.mem_memov.binet.hexagon.general.entry

trait GetAddress4[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY
  ): ADDRESS

  extension (entry: ENTRY)

    def getAddress4: ADDRESS =

      f(entry)
