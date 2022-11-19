package net.mem_memov.binet.memory.specific

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.inventory.specific.Argument
import net.mem_memov.binet.memory.specific.inventory.general.argument.{CheckAndTrimPermissive, CheckAndTrimRestrictive}

case class Inventory(
  next: Address,
  root: Element
)

object Inventory:

  given general.inventory.Next[Inventory, Address] with

    override
    def f(
      inventory: Inventory
    ): Address =

      inventory.next

  given [ARGUMENT, CONTENT, PATH](using
    CheckAndTrimPermissive[ARGUMENT, Address],
    general.element.Write[Element, PATH, CONTENT],
    general.address.ToPath[Address, PATH],
    general.address.ToContent[Address, CONTENT],
    general.address.Increment[Address]
  )(using
    argument: ARGUMENT
  ): general.inventory.Append[Inventory, Address] with

    override
    def f(
      inventory: Inventory,
      content: Address
    ): Either[String, Inventory] =

      for {
        trimmedContent <- argument.checkAndTrimPermissive(inventory.next, content)
        updatedRoot <- inventory.root.write(inventory.next.toPath, trimmedContent.toContent)
        newNext <- Right(inventory.next.increment)
      } yield inventory.copy(next = newNext, root = updatedRoot)

  given [ARGUMENT, CONTENT, PATH](using
    CheckAndTrimRestrictive[ARGUMENT, Address],
    general.element.Write[Element, PATH, CONTENT],
    general.address.ToPath[Address, PATH],
    general.address.ToContent[Address, CONTENT]
  )(using
    argument: ARGUMENT
  ): general.inventory.Update[Inventory, Address] with

    override def f(
      inventory: Inventory,
      destination: Address,
      content: Address
    ): Either[String, Inventory] =

      for {
        trimmedDestination <- argument.checkAndTrimRestrictive(inventory.next, destination)
        trimmedContent <- argument.checkAndTrimRestrictive(inventory.next, content)
        updatedRoot <- inventory.root.write(trimmedDestination.toPath, trimmedContent.toContent)
      } yield inventory.copy(root = updatedRoot)

  given [ARGUMENT, CONTENT, PATH](using
    CheckAndTrimRestrictive[ARGUMENT, Address],
    general.element.Read[Element, PATH, CONTENT],
    general.address.ToPath[Address, PATH],
    general.address.TrimBig[Address],
    general.content.ToAddress[CONTENT, Address]
  )(using
    argument: ARGUMENT
  ): general.inventory.Read[Inventory, Address] with

    override
    def f(
      inventory: Inventory,
      origin: Address
    ): Either[String, Address] =

      for {
        trimmedOrigin <- argument.checkAndTrimRestrictive(inventory.next, origin)
        content <- inventory.root.read(trimmedOrigin.toPath)
      } yield content.toAddress.trimBig
