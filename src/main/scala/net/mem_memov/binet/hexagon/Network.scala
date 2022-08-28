package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory

class Network(
  private val inventory: Inventory
):

  def dot: Option[Dot] =
    inventory.append(Entry.empty).map { address =>
      new Dot(inventory, address, Entry.empty)
    }

  def dot(address: memory.Address): Option[Dot] =
    inventory.read(address).map { entry =>
      new Dot(inventory, address, entry)
    }
