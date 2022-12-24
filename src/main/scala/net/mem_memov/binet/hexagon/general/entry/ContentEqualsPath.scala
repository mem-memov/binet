package net.mem_memov.binet.hexagon.general.entry

trait ContentEqualsPath[ENTRY]:

  def f(
    entry: ENTRY
  ): Boolean

  extension (entry: ENTRY)

    def contentEqualsPath: Boolean =

      f(entry)
