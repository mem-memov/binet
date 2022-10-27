package net.mem_memov.binet.memory.tree.treeInventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._

trait UnusedPreparingContent(fail: String => Nothing) extends PreparingContent:

  def checkAndTrim(
    next: Address,
    content: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address] =

    fail("unexpected")
