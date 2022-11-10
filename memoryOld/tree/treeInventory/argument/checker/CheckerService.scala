package net.mem_memov.binet.memoryOld.tree.treeInventory.argument.checker

import net.mem_memov.binet.memory.Address
import net.mem_memov.binet.memory.tree.treeFactory.AddressFactory
import net.mem_memov.binet.memory.tree.treeInventory.argument.*

class CheckerService extends Checker:

  def checkType(
    next: Address,
    address: Address
  ): Either[String, Unit] =

    if !next.canCompare(address) then
      Left("Wrong address type")
    else
      Right(())

  def checkBoundaryRestrictively(
    next: Address,
    address: Address
  ): Either[String, Unit] =

    if next.isLessOrEqual(address) then
      Left("Address out of restrictive boundary")
    else
      Right(())

  def checkBoundaryPermissively(
    next: Address,
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Unit] =

    if next.isLessOrEqual(address) && !next.isEqual(addressFactory.zeroAddress) then
      Left("Address out of permissive boundary")
    else
      Right(())
