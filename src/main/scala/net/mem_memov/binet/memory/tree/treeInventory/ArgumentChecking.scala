package net.mem_memov.binet.memory.tree.treeInventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._
import net.mem_memov.binet.memory.tree.treeInventory.argumentChecking._

trait ArgumentChecking:

  def checkContentAndTrim(
    next: Address,
    content: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address]

object ArgumentChecking:

  def apply(): ArgumentChecking = new InventoryArgumentChecking()
