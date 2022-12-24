package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general

case class DotIdentifier(
  entry: Entry
)

object DotIdentifier:

  given (using
    general.entry.ContentEqualsPath[Entry]
  ): general.dotIdentifier.InDot[DotIdentifier] with

    override
    def f(
      dotIdentifier: DotIdentifier
    ): Boolean =

      dotIdentifier.entry.contentEqualsPath