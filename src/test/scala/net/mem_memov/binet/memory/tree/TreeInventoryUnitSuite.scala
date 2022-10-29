package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._
import net.mem_memov.binet.memory.tree.treeInventory._

class TreeInventoryUnitSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given AddressFactory = new UnusedAddressFactory(failMethod) {}
  given TraversalFactory = new UnusedTraversalFactory(failMethod) {}

  test("Inventory appends content") {

    val trimmedContentAddress = new UnusedAddress(failMethod) {}
    val contentAddress = new UnusedAddress(failMethod) {}

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
      override def checkAndTrimPermissive(next: Address, content: Address): Either[String, Address] =
        assert(next.equals(nextAddress))
        assert(content.equals(contentAddress))
        Right(trimmedContentAddress)

    val inventory = TreeInventory(nextAddress, rootElement, argument)

    for {
      result <- inventory.append(contentAddress)
    } yield assert(result.next.equals(newNextAddress) && result.root.equals(updatedRootElement))
  }

  test("Inventory updates content") {

    val contentAddress = new UnusedAddress(failMethod) {}
    val trimmedContentAddress = new UnusedAddress(failMethod) {}

    val destinationAddress = new UnusedAddress(failMethod) {}
    val trimmedDestinationAddress = new UnusedAddress(failMethod) {}

    val nextAddress = new UnusedAddress(failMethod) {}

    val updatedRootElement = new UnusedElement(failMethod) {}

    val rootElement = new UnusedElement(failMethod):
      override def write(destination: Address, content: Address): Either[String, Element] =
        assert(destination.equals(trimmedDestinationAddress))
        assert(content.equals(trimmedContentAddress))
        Right(updatedRootElement)

    val argument = new UnusedArgument(failMethod):
      override def checkAndTrimRestrictive(next: Address, content: Address): Either[String, Address] =
        assert(next.equals(nextAddress))
        assert(content.equals(destinationAddress) || content.equals(contentAddress))
        if content.equals(contentAddress) then Right(trimmedContentAddress) else Right(trimmedDestinationAddress)

    val inventory = TreeInventory(nextAddress, rootElement, argument)

    for {
      result <- inventory.update(destinationAddress, contentAddress)
    } yield assert(result.next.equals(nextAddress) && result.root.equals(updatedRootElement))
  }

  test("Inventory reads content") {

    val trimmedContentAddress = new UnusedAddress(failMethod) {}

    val contentAddress = new UnusedAddress(failMethod):
      override def trimBig: Address = trimmedContentAddress

    val originAddress = new UnusedAddress(failMethod) {}
    val trimmedOriginAddress = new UnusedAddress(failMethod) {}

    val nextAddress = new UnusedAddress(failMethod) {}

    val rootElement = new UnusedElement(failMethod):
      override def read(origin: Address): Either[String, Address] =
        assert(origin.equals(trimmedOriginAddress))
        Right(contentAddress)

    val argument = new UnusedArgument(failMethod):
      override def checkAndTrimRestrictive(next: Address, content: Address): Either[String, Address] =
        assert(next.equals(nextAddress))
        assert(content.equals(originAddress))
        Right(trimmedOriginAddress)

    val inventory = TreeInventory(nextAddress, rootElement, argument)

    for {
      result <- inventory.read(originAddress)
    } yield assert(result.equals(trimmedContentAddress))
  }

