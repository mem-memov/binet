package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.TreeInventory

/**
 * Inventory implements the idea of an address database with stores its own addresses but at different places.
 * It grows only by adding addresses that are already in use.
 * It rejects addresses outside its boundary.
 */
trait Inventory[I]:

  def nextInInventory(
    inventory: I
  ): Address

  def appendToInventory(
    inventory: I,
    content: Address
  ): Either[String, I]

  def updateInventory(
    inventory: I,
    destination: Address,
    content: Address
  ): Either[String, I]

  def readInventory(
    inventory: I,
    origin: Address
  ): Either[String, Address]

  def foreachSliceInInventory(
    inventory: I,
    f: Array[Byte] => Unit
  ): Unit

object Inventory:

  extension [I](inventory: I)(using i: Inventory[I])

    def next(): Address =

      i.nextInInventory(inventory)

    def update(
      destination: Address,
      content: Address
    ): Either[String, I] =

      i.updateInventory(inventory, destination, content)

    def append(
      content: Address
    ): Either[String, I] =

      i.appendToInventory(inventory, content)

    def read(
      origin: Address
    ): Either[String, Address] =

      i.readInventory(inventory, origin)

    def foreachSlice(
      f: Array[Byte] => Unit
    ): Unit =

      i.foreachSliceInInventory(inventory, f)
