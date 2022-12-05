package net.mem_memov.binet.hexagon.general.arrow

trait GetEntry[ARROW, ENTRY]:

  def f(
    arrow: ARROW
  ): ENTRY

  extension (arrow: ARROW)

    def getEntry: ENTRY =

      f(arrow)
