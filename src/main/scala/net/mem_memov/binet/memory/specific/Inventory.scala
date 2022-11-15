package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.inventory.specific.SpecificArgument
import net.mem_memov.binet.memory.specific.inventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}

case class Inventory(
  next: Address,
  root: Element
)

object Inventory:

  given general.inventory.Next[Inventory, Address] with

    override
    def nextInInventory(
      inventory: Inventory
    ): Address =

      inventory.next

  given [ARGUMENT](using
    argument: ARGUMENT
  )(using
    CheckAndTrimPermissive[ARGUMENT, Address],
    general.element.Write[Element, Path, Content],
    general.address.ToPath[Address, Path],
    general.address.ToContent[Address, Content],
    general.address.Increment[Address]
  ): general.inventory.Append[Inventory, Address] with

    override
    def appendToInventory(
      inventory: Inventory,
      content: Address
    ): Either[String, Inventory] =

      for {
        trimmedContent <- argument.checkAndTrimPermissive(inventory.next, content)
        updatedRoot <- inventory.root.write(inventory.next.toPath, trimmedContent.toContent)
        newNext <- Right(inventory.next.increment)
      } yield inventory.copy(next = newNext, root = updatedRoot)

  given [ARGUMENT](using
    argument: ARGUMENT
  )(using
    CheckAndTrimRestrictive[ARGUMENT, Address],
    general.element.Write[Element, Path, Content],
    general.address.ToPath[Address, Path],
    general.address.ToContent[Address, Content]
  ): general.inventory.Update[Inventory, Address] with

    override def updateInventory(
      inventory: Inventory,
      destination: Address,
      content: Address
    ): Either[String, Inventory] =

      for {
        trimmedDestination <- argument.checkAndTrimRestrictive(inventory.next, destination)
        trimmedContent <- argument.checkAndTrimRestrictive(inventory.next, content)
        updatedRoot <- inventory.root.write(trimmedDestination.toPath, trimmedContent.toContent)
      } yield inventory.copy(root = updatedRoot)

  given [ARGUMENT](using
    argument: ARGUMENT
  )(using
    CheckAndTrimRestrictive[ARGUMENT, Address],
    general.element.Read[Element, Path, Content],
    general.address.ToPath[Address, Path],
    general.address.TrimBig[Address]
  ): general.inventory.Read[Inventory, Address] with

    override
    def readInventory(
      inventory: Inventory,
      origin: Address
    ): Either[String, Address] =

      for {
        trimmedOrigin <- argument.checkAndTrimRestrictive(inventory.next, origin)
        content <- inventory.root.read(trimmedOrigin.toPath)
      } yield content.toAddress.trimBig
