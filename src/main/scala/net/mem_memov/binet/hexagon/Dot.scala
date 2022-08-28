package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory

class Dot(
  private val inventory: Inventory,
  val address: memory.Address,
  private val entry: Entry
):

  def previousDot: Option[Dot] =
    for {
      dotEntry <- inventory.read(entry.address1)
    } yield new Dot(inventory, entry.address1, dotEntry)

  def nextDot: Option[Dot] =
    for {
      dotEntry <- inventory.read(entry.address2)
    } yield new Dot(inventory, entry.address2, dotEntry)

  def incrementSourceCount: Option[Dot] =
    val newEntry = entry.copy(address3 = entry.address3.increment)
    Option.when(inventory.update(address, newEntry))(new Dot(inventory, address, newEntry))

  def decrementSourceCount: Option[Dot] =
    for {
      count <- entry.address3.decrement
      newEntry = entry.copy(address3 = count)
      dot <- Option.when(inventory.update(address, newEntry))(new Dot(inventory, address, newEntry))
    } yield dot

  def incrementTargetCount: Option[Dot] =
    val newEntry = entry.copy(address4 = entry.address4.increment)
    Option.when(inventory.update(address, newEntry))(new Dot(inventory, address, newEntry))

  def decrementTargetCount: Option[Dot] =
    for {
      count <- entry.address4.decrement
      newEntry = entry.copy(address4 = count)
      dot <- Option.when(inventory.update(address, newEntry))(new Dot(inventory, address, newEntry))
    } yield dot

  def sourceArrow: Option[Arrow] =
    for {
      arrowEntry <- inventory.read(entry.address5)
    } yield new Arrow(inventory, entry.address5, arrowEntry)

  def targetArrow: Option[Arrow] =
    for {
      arrowEntry <- inventory.read(entry.address6)
    } yield new Arrow(inventory, entry.address6, arrowEntry)
