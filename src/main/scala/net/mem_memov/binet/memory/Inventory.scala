package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.tree.DefaultInventory

/**
 * Inventory implements the idea of an address database with stores its own addresses but at different places.
 * It grows only by adding addresses that are already in use.
 * It rejects addresses outside its boundary.
 */
trait Inventory:

  val next: Address

  def append(content: Address): Either[String, Inventory]

  def update(destination: Address, content: Address): Either[String, Inventory]

  def read(origin: Address): Either[String, Address]


