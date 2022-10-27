package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._
import net.mem_memov.binet.memory.tree.treeInventory._

class TreeInventoryUnitSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given AddressFactory = new UnusedAddressFactory(failMethod) {}

  test("Inventory appends content") {

    val trimmedContentAddress = new UnusedAddress(failMethod) {}

    lazy val contentAddress = new UnusedAddress(failMethod) {}

    val newNextAddress = new UnusedAddress(failMethod) {}

    val nextAddress = new UnusedAddress(failMethod):
      override def increment: Address =
        newNextAddress

    val updatedRootElement = new UnusedElement(failMethod) {}

    val rootElement = new UnusedElement(failMethod):
      override def write(destination: Address, content: Address): Either[String, Element] =
        assert(destination.equals(nextAddress))
        assert(content.equals(trimmedContentAddress))
        Right(updatedRootElement)

    val argument = new UnusedArgument(failMethod):
      override def checkAndTrimPermissive(next: Address, content: Address)(using addressFactory: AddressFactory): Either[String, Address] =
        assert(next.equals(nextAddress))
        assert(content.equals(contentAddress))
        Right(trimmedContentAddress)

    val inventory = TreeInventory(nextAddress, rootElement, argument)

    for {
      result <- inventory.append(contentAddress)
    } yield assert(result.next.equals(newNextAddress) && result.root.equals(updatedRootElement))
  }



