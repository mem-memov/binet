package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Network(
  private val inventory: Ref[Inventory],
  val lastDot: Dot
):

  def dot: Task[Network] =
    for {
      m <- inventory.modify { inventory =>
        val modifiedInventory: Task[Inventory] = inventory.append(Entry.empty)
        (modifiedInventory, modifiedInventory)
      }
    } yield new Network(
      updatedInventory,
      new Dot(updatedInventory, updatedInventory.address, updatedInventory.entry)
    )

  def dot(address: memory.Address): Task[Dot] =
    for {
      entry <- inventory.get.map(_.read(address))
    } yield new Dot(inventory, address, entry)
