package net.mem_memov.binet.hexagon.general.source

trait InArrowTail[SOURCE, ARROW]:

  def f(
    source: SOURCE,
    arrow: ARROW
  ): Boolean

  extension (source: SOURCE)

    def inArrowTail(
      arrow: ARROW
    ): Boolean =

      f(source, arrow)
