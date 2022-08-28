package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory

class Arrow(
  private val inventory: Inventory,
  val address: memory.Address,
  private val entry: Entry
):

  def hasSourceDot(source: Dot): Boolean =
    entry.address1 == source.address
  
  def sourceDot: Option[Dot] =
    Option.unless(entry.address1.isZero) {
      new Dot(
        inventory,
        entry.address1,
        inventory.read(entry.address1)
      )
    }

  def previousSourceArrow: Option[Arrow] =
    Option.unless(entry.address2.isZero) {
      new Arrow(
        inventory,
        entry.address2,
        inventory.read(entry.address2)
      )
    }


  def nextSourceArrow: Option[Arrow] =
    Option.unless(entry.address3.isZero) {
      new Arrow(
        inventory,
        entry.address3,
        inventory.read(entry.address3)
      )
    }

  def hasTargetDot(target: Dot): Boolean =
    entry.address4 == target.address

  def targetDot: Option[Dot] =
    Option.unless(entry.address4.isZero) {
      new Dot(
        inventory,
        entry.address4,
        inventory.read(entry.address4)
      )
    }

  def previousTargetArrow: Option[Arrow] =
    Option.unless(entry.address5.isZero) {
      new Arrow(
        inventory,
        entry.address5,
        inventory.read(entry.address5)
      )
    }

  def nextTargetArrow: Option[Arrow] =
    Option.unless(entry.address6.isZero) {
      new Arrow(
        inventory,
        entry.address6,
        inventory.read(entry.address6)
      )
    }