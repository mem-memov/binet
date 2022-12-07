package net.mem_memov.binet.hexagon.general.entry

trait GetAddress3[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY
  ): ADDRESS

  extension (entry: ENTRY)

    def getAddress3: ADDRESS =

      f(entry)
