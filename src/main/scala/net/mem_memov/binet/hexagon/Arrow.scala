package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory
import zio.*

class Arrow(
  private val inventory: Ref[Inventory],
  val address: memory.Address,
  private val entry: Entry
):

  def hasSourceDot(source: Dot): Boolean =
    entry.address1 == source.address
  
  def sourceDot: Task[Option[Dot]] =
    Dot.getDot(entry.address1, inventory)

  def previousSourceArrow: Task[Option[Arrow]] =
    Arrow.getArrow(entry.address2, inventory)

  def nextSourceArrow: Task[Option[Arrow]] =
    Arrow.getArrow(entry.address3, inventory)

  def hasTargetDot(target: Dot): Boolean =
    entry.address4 == target.address

  def targetDot: Task[Option[Dot]] =
    Dot.getDot(entry.address4, inventory)

  def previousTargetArrow: Task[Option[Arrow]] =
    Arrow.getArrow(entry.address5, inventory)

  def nextTargetArrow: Task[Option[Arrow]] =
    Arrow.getArrow(entry.address6, inventory)

object Arrow:

  def getArrow(address: memory.Address, inventory: Ref[Inventory]): Task[Option[Arrow]] =
    if address.isZero then
      ZIO.succeed(None)
    else
      for {
        foundEntry <- inventory.get.flatMap { inventory =>
          ZIO.fromEither(inventory.read(address))
        }
      } yield Some(new Arrow(inventory, address, foundEntry))