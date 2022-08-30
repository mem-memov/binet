package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Dot(
  private val inventory: Ref[Inventory],
  val address: memory.Address,
  private val entry: Entry
):

  def previousDot: Task[Option[Dot]] =
    Dot.getDot(entry.address1, inventory)

  def nextDot: Task[Option[Dot]] =
    Dot.getDot(entry.address2, inventory)

  def incrementSourceCount: Task[Dot] =
    entry.address3.increment match
      case Left(error) =>
        ZIO.fail(error)
      case Right(incrementedAddress) =>
        val newEntry = entry.copy(address3 = incrementedAddress)
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
        ZIO.fail(error)
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
    entry.address4.increment match
      case Left(error) =>
        ZIO.fail(error)
      case Right(incrementedAddress) =>
        val newEntry = entry.copy(address4 = incrementedAddress)
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
        ZIO.fail(error)
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
    Arrow.getArrow(entry.address5, inventory)

  def targetArrow: Task[Option[Arrow]] =
    Arrow.getArrow(entry.address6, inventory)

  def createArrowOnSourceDot(targetDot: Dot): Task[Arrow] =

    val newArrowEntry = Entry(
      address,
      memory.Address.zero,
      previousSourceArrow.address,
      targetDot.address,
      memory.Address.zero,
      memory.Address.zero,
    )
    for {
      _ <-
    } yield ???

  def createArrowOnTargetDot(entry: Entry): Task[Arrow] =


object Dot:

  def getDot(address: memory.Address, inventory: Ref[Inventory]): Task[Option[Dot]] =
    if address.isZero then
      ZIO.succeed(None)
    else
      for {
        foundEntry <- inventory.get.flatMap { inventory =>
          ZIO.fromEither(inventory.read(address))
        }
      } yield Some(new Dot(inventory, address, foundEntry))
