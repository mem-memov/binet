package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory

class Network(
  private val inventory: Inventory
):

  def dot: Dot =
    new Dot(
      inventory,
      inventory.append(Entry.empty),
      Entry.empty
    )

  def dot(address: memory.Address): Dot =
    new Dot(
      inventory,
      address,
      inventory.read(address)
    )
