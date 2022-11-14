package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general.inventory.InventoryAppender
import net.mem_memov.binet.memory.specific.specificInventory.specific.SpecificArgument
import net.mem_memov.binet.memory.specific.specificInventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}
import net.mem_memov.binet.memory.general.element.ElementWriter
import net.mem_memov.binet.memory.general.address.{AddressToPathConverter, AddressToContentConverter, AddressIncrementer}

case class SpecificInventory(
  next: SpecificAddress,
  root: SpecificElement
)

object SpecificInventory:

  given [ARGUMENT](using
    argument: ARGUMENT
  )(using
    CheckAndTrimPermissive[ARGUMENT, SpecificAddress],
    ElementWriter[SpecificElement, SpecificPath, SpecificContent],
    AddressToPathConverter[SpecificAddress, SpecificPath],
    AddressToContentConverter[SpecificAddress, SpecificContent],
    AddressIncrementer[SpecificAddress]
  ): InventoryAppender[SpecificInventory, SpecificAddress] with

    override
    def appendToInventory(
      inventory: SpecificInventory,
      content: SpecificAddress
    ): Either[String, SpecificInventory] =

      for {
        trimmedContent <- argument.checkAndTrimPermissive(inventory.next, content)
        updatedRoot <- inventory.root.write(inventory.next.toPath, trimmedContent.toContent)
        newNext <- Right(inventory.next.increment)
      } yield inventory.copy(next = newNext, root = updatedRoot)
