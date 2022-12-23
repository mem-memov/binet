package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.hexagon.general.Position
import net.mem_memov.binet.memory.specific.Address

case class Entry(
  position: Position,
  path: Address,
  content: Address
)

object Entry
  
  