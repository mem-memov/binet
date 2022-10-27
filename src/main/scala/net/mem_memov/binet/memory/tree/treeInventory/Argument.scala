package net.mem_memov.binet.memory.tree.treeInventory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeInventory.argument.ArgumentService

trait Argument:

  def checkAndTrimPermissive(
    next: Address,
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address]

  def checkAndTrimRestrictive(
    next: Address,
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address]

object Argument:

  def apply(): Argument = new ArgumentService()
