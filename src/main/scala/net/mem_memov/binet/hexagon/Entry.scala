package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory
import net.mem_memov.binet.memory.tree
import net.mem_memov.binet.memory.tree.DefaultFactory

private[hexagon] case class Entry(
  address1: memory.Address,
  address2: memory.Address,
  address3: memory.Address,
  address4: memory.Address,
  address5: memory.Address,
  address6: memory.Address
)

private[hexagon] object Entry:

  val empty: Entry = Entry(
    DefaultFactory.emptyAddress,
    tree.DefaultFactory.emptyAddress,
    tree.DefaultFactory.emptyAddress,
    tree.DefaultFactory.emptyAddress,
    tree.DefaultFactory.emptyAddress,
    tree.DefaultFactory.emptyAddress,
  )
