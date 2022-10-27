package net.mem_memov.binet.memory.tree.treeInventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._

trait UnusedArgument(fail: String => Nothing) extends Argument:

  def checkAndTrimPermissive(
    next: Address,
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address] =

    fail("unexpected")

  def checkAndTrimRestrictive(
    next: Address,
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address] =

    fail("unexpected")