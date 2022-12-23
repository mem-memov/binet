package net.mem_memov.binet.hexagon.general.entry

trait GetContent[ENTRY, ADDRESS]:

  def f(
    entry: ENTRY
  ): ADDRESS

  extension (entry: ENTRY)

    def getContent: ADDRESS =

      f(entry)
