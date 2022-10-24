package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.inventory.DefaultInventory

/**
 * Inventory implements the idea of an address database with stores its own addresses but at different places.
 * It grows only by adding addresses that are already in use.
 * It rejects addresses outside its boundary.
 */
trait Inventory extends NextAddressInventory with AppendableInventory with UpdatableInventory with ReadableInventory

trait NextAddressInventory:

  val next: Address

trait AppendableInventory:

  def append(content: Address): Either[String, Inventory]
  
trait UpdatableInventory:

  def update(destination: Address, content: Address): Either[String, Inventory]
  
trait ReadableInventory:

  def read(origin: Address): Either[String, Address]


