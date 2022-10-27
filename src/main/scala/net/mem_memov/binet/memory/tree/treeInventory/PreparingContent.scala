package net.mem_memov.binet.memory.tree.treeInventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._
import net.mem_memov.binet.memory.tree.treeInventory.preparingContent._

trait PreparingContent:

  def checkAndTrim(
    next: Address,
    content: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address]

object PreparingContent:

  def apply(): PreparingContent = new PreparingContentService()
