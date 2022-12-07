package net.mem_memov.binet.hexagon.general.entry

trait GetAddress1[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY
  ): ADDRESS

  extension (entry: ENTRY)

    def getAddress1: ADDRESS =

      f(entry)
