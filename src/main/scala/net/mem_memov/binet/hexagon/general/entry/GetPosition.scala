package net.mem_memov.binet.hexagon.general.entry

import net.mem_memov.binet.hexagon.general.Position

trait GetPosition[ENTRY]:

  def f(
    entry: ENTRY
  ): Position

  extension (entry: ENTRY)

    def getPosition: Position =

      f(entry)
