package net.mem_memov.binet.hexagon

import net.mem_memov.binet.hexagon.Arrow.updateArrow
import net.mem_memov.binet.memory
import zio.*

/**
 * An arrow in a network connects two dots.
 * It consists of two parts. One pints to a source dot. Another to a target dot.
 * 1) source dot
 * 2) previous source arrow
 * 3) next source arrow
 * 4) target dot
 * 5) previous target arrow
 * 6) next target arrow
 */
class Arrow(
  private val inventory: Ref[Inventory],
  val address: memory.Address,
  private val entry: Entry
):

  def hasSourceDot(source: Dot): Boolean =
    entry.address1 == source.address

  def sourceDot: Task[Dot] =
    for {
      dotOption <- Dot.getDot(inventory, entry.address1)
      dot <- dotOption match
        case Some(dot) => ZIO.succeed(dot)
        case None => ZIO.fail(Exception("Arrow without source."))
    } yield dot

  def previousSourceArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address2)

  def nextSourceArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address3)

  def hasTargetDot(source: Dot): Boolean =
    entry.address4 == source.address

  def targetDot: Task[Dot] =
    for {
      dotOption <- Dot.getDot(inventory, entry.address4)
      dot <- dotOption match
        case Some(dot) => ZIO.succeed(dot)
        case None => ZIO.fail(Exception("Arrow without target."))
    } yield dot

  def previousTargetArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address5)

  def nextTargetArrow: Task[Option[Arrow]] =
    Arrow.getArrow(inventory, entry.address6)

  def setNextSourceArrow(arrow: Arrow): Task[Arrow] =
    val newEntry = entry.copy(address3 = arrow.address)
    for {
      _ <- updateArrow(inventory, address, newEntry)
    } yield new Arrow(inventory, address, newEntry)

  def setNextTargetArrow(arrow: Arrow): Task[Arrow] =
    val newEntry = entry.copy(address6 = arrow.address)
    for {
      _ <- updateArrow(inventory, address, newEntry)
    } yield new Arrow(inventory, address, newEntry)

  def search: Search =
    new Search(this)

object Arrow:

  def getArrow(inventory: Ref[Inventory], address: memory.Address): Task[Option[Arrow]] =
    Network.getEntry(inventory, address).map { entryOption =>
      entryOption.map { entry =>
        new Arrow(inventory, address, entry)
      }
    }

  def updateArrow(inventory: Ref[Inventory], address: memory.Address, entry: Entry): Task[Unit] =
    Network.updateEntry(inventory, address, entry)