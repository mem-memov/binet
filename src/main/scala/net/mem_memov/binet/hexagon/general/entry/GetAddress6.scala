package net.mem_memov.binet.hexagon.general.entry

trait GetAddress6[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY
  ): ADDRESS

  extension (entry: ENTRY)

    def getAddress6: ADDRESS =

      f(entry)
