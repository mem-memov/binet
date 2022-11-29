package net.mem_memov.binet.hexagon.specific

import net.mem_memov.binet.memory.specific.Address

case class Arrow(
  dictionary: Dictionary,
  address: Address,
  entry: Entry
)
