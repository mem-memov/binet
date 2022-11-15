package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.inventory.{InventoryAppender, InventoryNextAddressProvider, InventoryReader, InventoryUpdater}
import net.mem_memov.binet.memory.general.element.{ElementReader, ElementWriter}
import net.mem_memov.binet.memory.general.address.{AddressIncrementer, AddressToContentConverter, AddressToPathConverter, AddressTrimmer}
import net.mem_memov.binet.memory.specific.specificInventory.specific.SpecificArgument
import net.mem_memov.binet.memory.specific.specificInventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}

case class SpecificInventory(
  next: SpecificAddress,
  root: SpecificElement
)

object SpecificInventory:

  given InventoryNextAddressProvider[SpecificInventory, SpecificAddress] with

    override
    def nextInInventory(
      inventory: SpecificInventory
    ): SpecificAddress =

      inventory.next

  given [
    ARGUMENT
  ](using
    argument: ARGUMENT
  )(using
    CheckAndTrimPermissive[ARGUMENT],
    ElementWriter[SpecificElement],
    AddressToPathConverter[SpecificAddress],
    AddressToContentConverter[SpecificAddress],
    AddressIncrementer[SpecificAddress]
  ): InventoryAppender[SpecificInventory] with

    override
    def appendToInventory[
      ADDRESS
    ](
      inventory: SpecificInventory,
      content: ADDRESS
    ): Either[String, SpecificInventory] =

      for {
        trimmedContent <- argument.checkAndTrimPermissive[SpecificAddress](inventory.next, content)
        updatedRoot <- inventory.root.write(inventory.next.toPath, trimmedContent.toContent)
        newNext <- Right(inventory.next.increment)
      } yield inventory.copy(next = newNext, root = updatedRoot)

  given [ARGUMENT](using
    argument: ARGUMENT
  )(using
    CheckAndTrimRestrictive[ARGUMENT],
    ElementWriter[SpecificElement],
    AddressToPathConverter[SpecificAddress],
    AddressToContentConverter[SpecificAddress]
  ): InventoryUpdater[SpecificInventory] with

    override def updateInventory[
      ADDRESS
    ](
      inventory: SpecificInventory,
      destination: ADDRESS,
      content: ADDRESS
    ): Either[String, SpecificInventory] =

      for {
        trimmedDestination <- argument.checkAndTrimRestrictive(inventory.next, destination)
        trimmedContent <- argument.checkAndTrimRestrictive(inventory.next, content)
        updatedRoot <- inventory.root.write(trimmedDestination.toPath, trimmedContent.toContent)
      } yield inventory.copy(root = updatedRoot)

  given [
    ARGUMENT : CheckAndTrimRestrictive
  ](using
    argument: ARGUMENT
  )(using
    ElementReader[SpecificElement],
    AddressToPathConverter[SpecificAddress],
    AddressTrimmer[SpecificAddress]
  ): InventoryReader[SpecificInventory] with

    override
    def readInventory[
      ADDRESS
    ](
      inventory: SpecificInventory,
      origin: SpecificAddress
    ): Either[String, SpecificAddress] =

      for {
        trimmedOrigin <- argument.checkAndTrimRestrictive(inventory.next, origin)
        content <- inventory.root.read(trimmedOrigin.toPath)
      } yield content.toAddress.trimBig
