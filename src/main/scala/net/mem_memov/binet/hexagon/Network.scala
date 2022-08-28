package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Network(
  private val inventory: Inventory,
  val lastDot: Dot
):

  def dot: Task[Network] =
    for {
      updatedInventory <- inventory.append(Entry.empty)
    } yield new Network(
      updatedInventory,
      new Dot(updatedInventory, updatedInventory.address, updatedInventory.entry)
    )

  def dot(address: memory.Address): Dot =
    new Dot(
      inventory,
      address,
      inventory.read(address)
    )
