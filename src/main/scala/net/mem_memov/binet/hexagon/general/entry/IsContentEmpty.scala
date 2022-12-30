package net.mem_memov.binet.hexagon.general.entry

trait IsContentEmpty[ENTRY]:

  def f(
    entry: ENTRY
  ): Boolean

  extension (entry: ENTRY)

    def isContentEmpty: Boolean =

      f(entry)
