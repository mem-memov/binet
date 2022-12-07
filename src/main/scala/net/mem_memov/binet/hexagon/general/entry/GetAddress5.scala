package net.mem_memov.binet.hexagon.general.entry

trait GetAddress5[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY
  ): ADDRESS

  extension (entry: ENTRY)

    def getAddress5: ADDRESS =

      f(entry)
