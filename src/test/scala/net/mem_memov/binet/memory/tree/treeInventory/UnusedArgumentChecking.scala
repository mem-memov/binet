package net.mem_memov.binet.memory.tree.treeInventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._

trait UnusedArgumentChecking(fail: String => Nothing) extends ArgumentChecking:

  def checkContentAndTrim(
    next: Address,
    content: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address] =

    fail("unexpected")
