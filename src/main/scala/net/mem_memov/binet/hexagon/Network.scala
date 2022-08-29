package net.mem_memov.binet.hexagon

import zio.*
import net.mem_memov.binet.memory

class Network(
  private val inventory: Ref[Inventory]
):

  def dot: Task[Dot] =
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

  def dot(address: memory.Address): Task[Dot] =
    for {
      entry <- inventory.get.flatMap { inventory =>
        ZIO.fromEither(inventory.read(address))
      }
    } yield new Dot(inventory, address, entry)
