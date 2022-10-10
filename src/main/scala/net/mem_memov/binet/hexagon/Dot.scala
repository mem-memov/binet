package net.mem_memov.binet.hexagon

import net.mem_memov.binet.hexagon.Dot.updateDot
import zio.*
import net.mem_memov.binet.memory

/**
 * A dot in a network.
 * It has 3 parts. One part for direct connection to two other dots.
 * One part for counting arrows of both directions.
 * One part for connection to arrows.
 * 1) next dot
 * 2) previous dot
 * 3) source arrow counter
 * 4) target arrow counter
 * 5) first source arrow
 * 6) first target arrow
 */
class Dot(
  private val inventory: Ref[Inventory],
  val address: memory.Address,
  private val entry: Entry
):

  def previousDot: Task[Option[Dot]] =
    Dot.getDot(inventory, entry.address1)

  def nextDot: Task[Option[Dot]] =
    Dot.getDot(inventory, entry.address2)

  def incrementSourceCount: Task[Dot] =
    val newEntry = entry.copy(address3 = entry.address3.increment)
    for {
      _ <- inventory.modify { inventory =>
        inventory.update(address, newEntry) match
          case Left(error) => (ZIO.fail(error), inventory)
          case Right(updatedInventory) => (ZIO.unit, updatedInventory)
      }
    } yield new Dot(inventory, address, newEntry)

  def decrementSourceCount: Task[Dot] =
    entry.address3.decrement match
      case Left(error) =>
        ZIO.fail(Exception(error))
      case Right(decrementedAddress) =>
        val newEntry = entry.copy(address3 = decrementedAddress)
        for {
          _ <- inventory.modify { inventory =>
            inventory.update(address, newEntry) match
              case Left(error) => (ZIO.fail(error), inventory)
              case Right(updatedInventory) => (ZIO.unit, updatedInventory)
          }
        } yield new Dot(inventory, address, newEntry)

  def incrementTargetCount: Task[Dot] =
    val newEntry = entry.copy(address4 = entry.address4.increment)
    for {
      _ <- inventory.modify { inventory =>
        inventory.update(address, newEntry) match
          case Left(error) => (ZIO.fail(error), inventory)
          case Right(updatedInventory) => (ZIO.unit, updatedInventory)
      }
    } yield new Dot(inventory, address, newEntry)

  def decrementTargetCount: Task[Dot] =
    entry.address4.decrement match
      case Left(error) =>
        ZIO.fail(Exception(error))
      case Right(decrementedAddress) =>
        val newEntry = entry.copy(address4 = decrementedAddress)
        for {
          _ <- inventory.modify { inventory =>
            inventory.update(address, newEntry) match
              case Left(error) => (ZIO.fail(error), inventory)
              case Right(updatedInventory) => (ZIO.unit, updatedInventory)
          }
        } yield new Dot(inventory, address, newEntry)

  def sourceArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address5)

  def targetArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address6)

  def setSourceArrow(arrow: Arrow): Task[Dot] =
    val newEntry = entry.copy(address5 = arrow.address)
    for {
      _ <- updateDot(inventory, address, newEntry)
    } yield new Dot(inventory, address, newEntry)

  def setTargetArrow(arrow: Arrow): Task[Dot] =
    val newEntry = entry.copy(address6 = arrow.address)
    for {
      _ <- updateDot(inventory, address, newEntry)
    } yield new Dot(inventory, address, newEntry)



object Dot:

  def getDot(inventory: Ref[Inventory], address: memory.Address): Task[Option[Dot]] =
    Network.getEntry(inventory, address).map { entryOption =>
      entryOption.map { entry =>
        new Dot(inventory, address, entry)
      }
    }

  def updateDot(inventory: Ref[Inventory], address: memory.Address, entry: Entry): Task[Unit] =
    Network.updateEntry(inventory, address, entry)
