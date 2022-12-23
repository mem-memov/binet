package net.mem_memov.binet.hexagon.general.dotIdentifier

trait InArrow[DOT_IDENTIFIER]:

  def f(
    dotIdentifier: DOT_IDENTIFIER
  ): Boolean

  extension (dotIdentifier: DOT_IDENTIFIER)

    def inArrow: Boolean =

      f(dotIdentifier)
