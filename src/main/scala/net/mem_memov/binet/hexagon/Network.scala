package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Network(
  private val inventory: Ref[Inventory]
):

  def createDot: Task[Dot] =
    for {
      result <- inventory.modify { inventory =>
        inventory.append(Entry.empty) match
          case Left(error) =>
            (ZIO.fail(Exception("Dot not created")), inventory)
          case Right(modifiedInventory) =>
            (ZIO.succeed(modifiedInventory.resultAddress -> Entry.empty), modifiedInventory)
      }.flatten
      (address, entry) = result
    } yield new Dot(inventory, address, entry)

  def getDot(address: memory.Address): Task[Dot] =
    for {
      entry <- inventory.get.flatMap { inventory =>
        ZIO.fromEither[String, Entry](inventory.read(address)).mapError(Exception(_))
      }
    } yield new Dot(inventory, address, entry)

  def createArrow(entry: Entry): Task[Arrow] =
    for {
      result <- inventory.modify { inventory =>
        inventory.append(Entry.empty) match
          case Left(error) =>
            (ZIO.fail(Exception("Arrow not created")), inventory)
          case Right(modifiedInventory) =>
            (ZIO.succeed(modifiedInventory.resultAddress -> Entry.empty), modifiedInventory)
      }.flatten
      (address, entry) = result
    } yield new Arrow(inventory, address, entry)

object Network:

  def getEntry(inventory: Ref[Inventory], address: memory.Address): Task[Option[Entry]] =
    if address.isZero then
      ZIO.succeed(None)
    else
      for {
        foundEntry <- inventory.get.flatMap { inventory =>
          ZIO.fromEither[String, Entry](inventory.read(address)).mapError(Exception(_))
        }
      } yield Some(foundEntry)
  
  def updateEntry(inventory: Ref[Inventory], address: memory.Address, entry: Entry): Task[Unit] =
    for {
      entry <- inventory.get.flatMap { inventory =>
        ZIO.fromEither[String, Inventory](inventory.update(address, entry)).mapError(Exception(_))
      }
    } yield ()