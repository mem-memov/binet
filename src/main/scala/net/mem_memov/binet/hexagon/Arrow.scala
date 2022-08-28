package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory

class Arrow(
  private val inventory: Inventory,
  val address: memory.Address,
  private val entry: Entry
):
  
  def sourceDot: Option[Dot] =
    for {
      dotEntry <- inventory.read(entry.address1)
    } yield new Dot(inventory, entry.address1, dotEntry)

  def previousSourceArrow: Option[Arrow] =
    for {
      arrowEntry <- inventory.read(entry.address2)
    } yield new Arrow(inventory, entry.address2, arrowEntry)

  def nextSourceArrow: Option[Arrow] =
    for {
      arrowEntry <- inventory.read(entry.address3)
    } yield new Arrow(inventory, entry.address3, arrowEntry)
    
  def targetDot: Option[Dot] =
    for {
      dotEntry <- inventory.read(entry.address4)
    } yield new Dot(inventory, entry.address4, dotEntry)

  def previousTargetArrow: Option[Arrow] =
    for {
      arrowEntry <- inventory.read(entry.address5)
    } yield new Arrow(inventory, entry.address5, arrowEntry)

  def nextTargetArrow: Option[Arrow] =
    for {
      arrowEntry <- inventory.read(entry.address6)
    } yield new Arrow(inventory, entry.address6, arrowEntry)