package net.mem_memov.binet.memoryOld.tree.treeInventory.argument

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeInventory.*

class ArgumentService(
  checker: Checker,
  trimmer: Trimmer
)(using
  addressFactory: AddressFactory
) extends Argument:

  override
  def checkAndTrimPermissive(
    next: Address,
    address: Address
  ): Either[String, Address] =

    for {
      _ <- checker.checkType(next, address)
      trimmedAddress <- Right(trimmer.trim(address))
      _ <- checker.checkBoundaryPermissively(next, trimmedAddress)
    } yield trimmedAddress

  override
  def checkAndTrimRestrictive(
    next: Address,
    address: Address
  ): Either[String, Address] =

    for {
      _ <- checker.checkType(next, address)
      trimmedAddress <- Right(trimmer.trim(address))
      _ <- checker.checkBoundaryRestrictively(next, trimmedAddress)
    } yield trimmedAddress
