package net.mem_memov.binet.hexagon

import net.mem_memov.binet.memory
import net.mem_memov.binet.memory.Inventory.*
import net.mem_memov.binet.memory.tree.TreeAddress

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
      modifiedInventory1 <- inventory1.append(entry.address1.asInstanceOf[TreeAddress]) // TODO: remove type conversion
      modifiedInventory2 <- inventory2.append(entry.address2.asInstanceOf[TreeAddress])
      modifiedInventory3 <- inventory3.append(entry.address3.asInstanceOf[TreeAddress])
      modifiedInventory4 <- inventory4.append(entry.address4.asInstanceOf[TreeAddress])
      modifiedInventory5 <- inventory1.append(entry.address5.asInstanceOf[TreeAddress])
      modifiedInventory6 <- inventory1.append(entry.address6.asInstanceOf[TreeAddress])
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
      modifiedInventory1 <- inventory1.update(destination.asInstanceOf[TreeAddress], content.address1.asInstanceOf[TreeAddress]) // TODO:  remove type conversion
      modifiedInventory2 <- inventory2.update(destination.asInstanceOf[TreeAddress], content.address2.asInstanceOf[TreeAddress])
      modifiedInventory3 <- inventory3.update(destination.asInstanceOf[TreeAddress], content.address3.asInstanceOf[TreeAddress])
      modifiedInventory4 <- inventory4.update(destination.asInstanceOf[TreeAddress], content.address4.asInstanceOf[TreeAddress])
      modifiedInventory5 <- inventory1.update(destination.asInstanceOf[TreeAddress], content.address5.asInstanceOf[TreeAddress])
      modifiedInventory6 <- inventory1.update(destination.asInstanceOf[TreeAddress], content.address6.asInstanceOf[TreeAddress])
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
      address1 <- inventory1.read(origin.asInstanceOf[TreeAddress]) // TODO:  remove type conversion
      address2 <- inventory2.read(origin.asInstanceOf[TreeAddress])
      address3 <- inventory3.read(origin.asInstanceOf[TreeAddress])
      address4 <- inventory4.read(origin.asInstanceOf[TreeAddress])
      address5 <- inventory5.read(origin.asInstanceOf[TreeAddress])
      address6 <- inventory6.read(origin.asInstanceOf[TreeAddress])
    } yield Entry (
      address1,
      address2,
      address3,
      address4,
      address5,
      address6
    )



