package net.mem_memov.binet.memory.tree.treeInventory.argument

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeInventory.*

class ArgumentService extends Argument:

  override
  def checkAndTrimPermissive(
    next: Address,
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address] =

    if !next.canCompare(address) then
      Left("Wrong address type")
    else

      val trimmedContent = if address.isEmpty then addressFactory.zeroAddress else address.trimBig

      if next.isLessOrEqual(trimmedContent) && !next.isEqual(addressFactory.zeroAddress) then
        Left("Address out of boundary")
      else
        Right(trimmedContent)

  def checkAndTrimRestrictive(
    next: Address,
    address: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address] =

    if !next.canCompare(address) then
      Left("Inventory not updated: wrong address type")
    else

      val trimmedContent = if address.isEmpty then addressFactory.zeroAddress else address.trimBig

      if next.isLessOrEqual(trimmedContent) then
        Left("Inventory not updated: address out of boundary")
      else
        Right(trimmedContent)