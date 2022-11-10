package net.mem_memov.binet.memoryOld.tree.treeInventory.argument

import net.mem_memov.binet.memory.Address
import net.mem_memov.binet.memory.tree.treeFactory.AddressFactory

trait Checker:

  def checkType(
    next: Address,
    address: Address
  ): Either[String, Unit]

  def checkBoundaryRestrictively(
    next: Address,
    address: Address
  ): Either[String, Unit]

  def checkBoundaryPermissively(
    next: Address,
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Unit]