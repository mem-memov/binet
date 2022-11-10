package net.mem_memov.binet.memoryOld.tree.treeInventory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeInventory.argument._

trait Argument:

  def checkAndTrimPermissive(
    next: Address,
    address: Address
  ): Either[String, Address]

  def checkAndTrimRestrictive(
    next: Address,
    address: Address
  ): Either[String, Address]

object Argument:

  def apply(
    checker: Checker,
    trimmer: Trimmer
  )(using
    addressFactory: AddressFactory
  ): Argument =

    new ArgumentService(checker, trimmer)
