package net.mem_memov.binet.memory.inventory.defaultInventory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.factory.defaultFactory.AddressFactory

trait ArgumentChecking:

  def checkAndTrim(
    next: Address,
    content: Address
  )(using
    addressFactory: AddressFactory
  ): Either[String, Address]

object ArgumentChecking:

  def apply(): ArgumentChecking = new ArgumentChecking:

    override
    def checkAndTrim(
      next: Address,
      content: Address
    )(using
      addressFactory: AddressFactory
    ): Either[String, Address] =

      if !next.canCompare(content) then
        Left("Inventory not appended: wrong address type")
      else

        val trimmedContent = if content.isEmpty then addressFactory.zeroAddress else content.trimBig

        if next.isLessOrEqual(trimmedContent) && !next.isEqual(addressFactory.zeroAddress) then
          Left("Inventory not appended: content out of boundary")
        else
          Right(trimmedContent)
