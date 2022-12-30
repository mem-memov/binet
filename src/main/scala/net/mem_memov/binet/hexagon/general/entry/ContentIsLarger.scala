package net.mem_memov.binet.hexagon.general.entry

trait ContentIsLarger[ENTRY]:

  def f(
    entry: ENTRY,
    theOther: ENTRY
  ): Boolean

  extension (entry: ENTRY)

    def contentIsLarger(
      theOther: ENTRY
    ): Boolean =

      f(entry, theOther)
