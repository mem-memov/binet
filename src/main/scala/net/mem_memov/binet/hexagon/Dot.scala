package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Dot(
  private val inventory: UIO[Ref[Inventory]],
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
      incrementedAddress <- entry.address3.increment
      newEntry = entry.copy(address3 = incrementedAddress)
      _ <- inventory.modify { inventory =>
        ((), inventory.update(address, newEntry))
      }
    } yield new Dot(inventory, address, newEntry)

  def decrementSourceCount: Task[Dot] =
    for {
      decrementedAddress <- entry.address3.decrement
      newEntry = entry.copy(address3 = decrementedAddress)
      _ <- inventory.modify { inventory =>
        ((), inventory.update(address, newEntry))
      }
    } yield new Dot(inventory,address, newEntry)

  def incrementTargetCount: Task[Dot] =
    for {
      incrementedAddress <- entry.address4.increment
      newEntry = entry.copy(address4 = incrementedAddress)
      _ <- inventory.modify { inventory =>
        ((), inventory.update(address, newEntry))
      }
    } yield new Dot(inventory, address, newEntry)

  def decrementTargetCount: Task[Dot] =
    for {
      decrementedAddress <- entry.address4.decrement
      newEntry = entry.copy(address4 = decrementedAddress)
      _ <- inventory.modify { inventory =>
        ((), inventory.update(address, newEntry))
      }
    } yield new Dot(inventory,address, newEntry)

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
