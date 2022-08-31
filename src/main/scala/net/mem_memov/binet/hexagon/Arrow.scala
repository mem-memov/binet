package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory
import zio.*

/**
 * An arrow in a network connects two dots.
 * It consists of two parts.
 */
class Arrow(
  private val inventory: Ref[Inventory],
  val address: memory.Address,
  private val entry: Entry
):

  def hasSourceDot(source: Dot): Boolean =
    entry.address1 == source.address
  
  def sourceDot: Task[Option[Dot]] =
    Dot.getDot(inventory, entry.address1)

  def previousSourceArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address2)

  def nextSourceArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address3)

  def hasTargetDot(target: Dot): Boolean =
    entry.address4 == target.address

  def targetDot: Task[Option[Dot]] =
    Dot.getDot(inventory, entry.address4)

  def previousTargetArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address5)

  def nextTargetArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address6, inventory)

object Arrow:

  def getArrow(inventory: Ref[Inventory], address: memory.Address): Task[Option[Arrow]] =
    Network.getEntry(address).map { entryOption =>
      entryOption.map { entry =>
        new Arrow(inventory, address, entry)
      }
    }

  def updateArrow(inventory: Ref[Inventory], address: memory.Address, entry: Entry): Task[Unit] =
    Network.updateEntry(inventory, address, entry)