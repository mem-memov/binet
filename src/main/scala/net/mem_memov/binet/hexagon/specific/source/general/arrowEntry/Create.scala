package net.mem_memov.binet.hexagon.specific.source.general.arrowEntry

trait Create[ARROW_ENTRY, DOT, ENTRY]:

  def f(
    dot: DOT
  ): ENTRY

  extension (arrowEntry: ARROW_ENTRY)

    def create(
      dot: DOT
    ): ENTRY =

      f(dot)
