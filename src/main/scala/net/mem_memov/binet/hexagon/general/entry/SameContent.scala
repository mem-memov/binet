package net.mem_memov.binet.hexagon.general.entry

trait SameContent[ENTRY]:

  def f(
    entry: ENTRY,
    theOther: ENTRY
  ): Boolean

  extension (entry: ENTRY)

    def sameContent(
      theOther: ENTRY
    ): Boolean =

      f(entry, theOther)