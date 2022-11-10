package net.mem_memov.binet.memoryOld

import net.mem_memov.binet.memory.tree.TreeInventory

/**
 * Inventory implements the idea of an address database with stores its own addresses but at different places.
 * It grows only by adding addresses that are already in use.
 * It rejects addresses outside its boundary.
 */
trait Inventory[I, A : Address]:

  def nextInInventory(
    inventory: I
  ): A

  def appendToInventory(
    inventory: I,
    content: A
  ): Either[String, I]

  def updateInventory(
    inventory: I,
    destination: A,
    content: A
  ): Either[String, I]

  def readInventory(
    inventory: I,
    origin: A
  ): Either[String, A]

  def foreachSliceInInventory(
    inventory: I,
    f: Array[Byte] => Unit
  ): Unit

  extension (inventory: I)

    def next(): A =

      nextInInventory(inventory)

    def update(
      destination: A,
      content: A
    ): Either[String, I] =

      updateInventory(inventory, destination, content)

    def append(
      content: A
    ): Either[String, I] =

      appendToInventory(inventory, content)

    def read(
      origin: A
    ): Either[String, A] =

      readInventory(inventory, origin)

    def foreachSlice(
      f: Array[Byte] => Unit
    ): Unit =

      foreachSliceInInventory(inventory, f)
