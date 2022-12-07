package net.mem_memov.binet.hexagon.general.entry

trait GetAddress2[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY
  ): ADDRESS

  extension (entry: ENTRY)

    def getAddress2: ADDRESS =

      f(entry)
