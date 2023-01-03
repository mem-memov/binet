package net.mem_memov.binet.hexagon.general.entry

trait ClearContent[ENTRY]:

  def f(
    entry: ENTRY
  ): ENTRY

  extension (entry: ENTRY)

    def clearContent: ENTRY =

      f(entry)
