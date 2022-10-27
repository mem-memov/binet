package net.mem_memov.binet.memory.tree.defaultInventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.defaultFactory._
import net.mem_memov.binet.memory.tree.defaultInventory.argumentChecking._

trait ArgumentChecking:

  def checkContentAndTrim(
    next: Address,
    content: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address]

object ArgumentChecking:

  def apply(): ArgumentChecking = new DefaultArgumentChecking()
