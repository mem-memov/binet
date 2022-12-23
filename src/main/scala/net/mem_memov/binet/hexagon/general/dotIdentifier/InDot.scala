package net.mem_memov.binet.hexagon.general.dotIdentifier

trait InDot[DOT_IDENTIFIER]:

  def f(
    dotIdentifier: DOT_IDENTIFIER
  ): Boolean

  extension (dotIdentifier: DOT_IDENTIFIER)

    def inDot: Boolean =

      f(dotIdentifier)
