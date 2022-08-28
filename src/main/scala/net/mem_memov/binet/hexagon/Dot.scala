package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Dot(
  private val inventory: Inventory,
  val address: memory.Address,
  private val entry: Entry
):

  def previousDot: Option[Dot] =
    Option.unless(entry.address1.isZero) {
      new Dot(
        inventory,
        entry.address1,
        inventory.read(entry.address1)
      )
    }

  def nextDot: Option[Dot] =
    Option.unless(entry.address2.isZero) {
      new Dot(
        inventory,
        entry.address2,
        inventory.read(entry.address2)
      )
    }

  def incrementSourceCount: Task[Dot] =
    for {
      newEntry <- entry.copy(address3 = entry.address3.increment)
    } yield
    val newEntry = entry.copy(address3 = entry.address3.increment)
    inventory.update(address, newEntry)
    new Dot(
      inventory,
      address,
      newEntry
    )

  def decrementSourceCount: Dot =
    val newEntry = entry.copy(address3 = entry.address3.decrement)
    inventory.update(address, newEntry)
    new Dot(
      inventory,
      address,
      newEntry
    )

  def incrementTargetCount: Dot =
    val newEntry = entry.copy(address4 = entry.address4.increment)
    inventory.update(address, newEntry)
    new Dot(
      inventory,
      address,
      newEntry
    )

  def decrementTargetCount: Dot =
    val newEntry = entry.copy(address4 = entry.address3.decrement)
    inventory.update(address, newEntry)
    new Dot(
      inventory,
      address,
      newEntry
    )

  def sourceArrow: Option[Arrow] =
    Option.unless(entry.address5.isZero) {
      new Arrow(
        inventory,
        entry.address5,
        inventory.read(entry.address5)
      )
    }

  def targetArrow: Option[Arrow] =
    Option.unless(entry.address6.isZero) {
      new Arrow(
        inventory,
        entry.address6,
        inventory.read(entry.address6)
      )
    }
