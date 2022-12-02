package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general
import net.mem_memov.binet.memory.specific.Address

case class Arrow(
  address: Address,
  entry: Entry
)

object Arrow:

  given general.arrow.GetNextSourceArrow[Arrow] with

    override
    def f(
      arrow: Arrow
    ): Option[Arrow] =

      ???
