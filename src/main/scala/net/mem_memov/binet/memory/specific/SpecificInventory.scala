package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.specificInventory.specific.SpecificArgument
import net.mem_memov.binet.memory.specific.specificInventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}

case class SpecificInventory(
  next: SpecificAddress,
  root: SpecificElement
)

object SpecificInventory:

  given general.inventory.Next[SpecificInventory, SpecificAddress] with

    override
    def nextInInventory(
      inventory: SpecificInventory
    ): SpecificAddress =

      inventory.next

  given [ARGUMENT](using
    argument: ARGUMENT
  )(using
    CheckAndTrimPermissive[ARGUMENT, SpecificAddress],
    general.element.ElementWriter[SpecificElement, SpecificPath, SpecificContent],
    general.address.ToPath[SpecificAddress, SpecificPath],
    general.address.ToContent[SpecificAddress, SpecificContent],
    general.address.Increment[SpecificAddress]
  ): general.inventory.Append[SpecificInventory, SpecificAddress] with

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

  given [ARGUMENT](using
    argument: ARGUMENT
  )(using
    CheckAndTrimRestrictive[ARGUMENT, SpecificAddress],
    general.element.ElementWriter[SpecificElement, SpecificPath, SpecificContent],
    general.address.ToPath[SpecificAddress, SpecificPath],
    general.address.ToContent[SpecificAddress, SpecificContent]
  ): general.inventory.Update[SpecificInventory, SpecificAddress] with

    override def updateInventory(
      inventory: SpecificInventory,
      destination: SpecificAddress,
      content: SpecificAddress
    ): Either[String, SpecificInventory] =

      for {
        trimmedDestination <- argument.checkAndTrimRestrictive(inventory.next, destination)
        trimmedContent <- argument.checkAndTrimRestrictive(inventory.next, content)
        updatedRoot <- inventory.root.write(trimmedDestination.toPath, trimmedContent.toContent)
      } yield inventory.copy(root = updatedRoot)

  given [ARGUMENT](using
    argument: ARGUMENT
  )(using
    CheckAndTrimRestrictive[ARGUMENT, SpecificAddress],
    general.element.Read[SpecificElement, SpecificPath, SpecificContent],
    general.address.ToPath[SpecificAddress, SpecificPath],
    general.address.TrimBig[SpecificAddress]
  ): general.inventory.Read[SpecificInventory, SpecificAddress] with

    override
    def readInventory(
      inventory: SpecificInventory,
      origin: SpecificAddress
    ): Either[String, SpecificAddress] =

      for {
        trimmedOrigin <- argument.checkAndTrimRestrictive(inventory.next, origin)
        content <- inventory.root.read(trimmedOrigin.toPath)
      } yield content.toAddress.trimBig
