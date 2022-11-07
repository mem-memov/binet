package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory
import net.mem_memov.binet.memory.Inventory._

/**
 * Inventory keeps entries of arrows and dots
 */
private[hexagon] class Inventory(
  val resultAddress: memory.Address,
  val resultEntry: Entry,
  private val inventory1: memory.tree.TreeInventory,
  private val inventory2: memory.tree.TreeInventory,
  private val inventory3: memory.tree.TreeInventory,
  private val inventory4: memory.tree.TreeInventory,
  private val inventory5: memory.tree.TreeInventory,
  private val inventory6: memory.tree.TreeInventory,
):

  def append(entry: Entry): Either[String, Inventory] =
    for {
      modifiedInventory1 <- inventory1.append(entry.address1)
      modifiedInventory2 <- inventory2.append(entry.address2)
      modifiedInventory3 <- inventory3.append(entry.address3)
      modifiedInventory4 <- inventory4.append(entry.address4)
      modifiedInventory5 <- inventory1.append(entry.address5)
      modifiedInventory6 <- inventory1.append(entry.address6)
    } yield new Inventory(
      inventory1.next,
      entry,
      modifiedInventory1,
      modifiedInventory2,
      modifiedInventory3,
      modifiedInventory4,
      modifiedInventory5,
      modifiedInventory6,
    )

  def update(destination: memory.Address, content: Entry): Either[String, Inventory] =
    import net.mem_memov.binet.memory.Inventory._
    for {
      modifiedInventory1 <- inventory1.update(destination, content.address1)
      modifiedInventory2 <- inventory2.update(destination, content.address2)
      modifiedInventory3 <- inventory3.update(destination, content.address3)
      modifiedInventory4 <- inventory4.update(destination, content.address4)
      modifiedInventory5 <- inventory1.update(destination, content.address5)
      modifiedInventory6 <- inventory1.update(destination, content.address6)
    } yield Inventory(
      destination,
      content,
      modifiedInventory1,
      modifiedInventory2,
      modifiedInventory3,
      modifiedInventory4,
      modifiedInventory5,
      modifiedInventory6,
    )

  def read(origin: memory.Address): Either[String, Entry] =
    for {
      address1 <- inventory1.read(origin)
      address2 <- inventory2.read(origin)
      address3 <- inventory3.read(origin)
      address4 <- inventory4.read(origin)
      address5 <- inventory5.read(origin)
      address6 <- inventory6.read(origin)
    } yield Entry (
      address1,
      address2,
      address3,
      address4,
      address5,
      address6
    )



