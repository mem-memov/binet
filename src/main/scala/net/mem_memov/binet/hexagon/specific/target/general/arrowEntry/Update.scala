package net.mem_memov.binet.hexagon.specific.target.general.arrowEntry

trait Update[ARROW_ENTRY, DOT, ENTRY]:

  def f(
    entry: ENTRY,
    dot: DOT
  ): ENTRY

  extension (arrowEntry: ARROW_ENTRY)

    def update(
      entry: ENTRY,
      dot: DOT
    ): ENTRY =

      f(entry, dot)
