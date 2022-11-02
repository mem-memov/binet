package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._
import net.mem_memov.binet.memory.tree.treeInventory._

class TreeInventoryUnitSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given AddressFactory = new UnusedAddressFactory(failMethod) {}
  given TraversalFactory = new UnusedTraversalFactory(failMethod) {}

  test("Inventory appends content") {

    val writtenContent = new UnusedContent(failMethod) {}

    val trimmedContentAddress = new UnusedAddress(failMethod):
      override def toContent: Content =
        writtenContent

    val contentAddress = new UnusedAddress(failMethod) {}

    val destinationPath = new UnusedPath(failMethod) {}
    val newNextAddress = new UnusedAddress(failMethod) {}

    val nextAddress = new UnusedAddress(failMethod):
      override def increment: Address =
        newNextAddress
      override def toPath: Path =
        destinationPath

    val updatedRootElement = new UnusedElement(failMethod) {}

    val rootElement = new UnusedElement(failMethod):
      override def write(destination: Path, content: Content): Either[String, Element] =
        assert(destination.equals(destinationPath))
        assert(content.equals(writtenContent))
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

    val writtenContent = new UnusedContent(failMethod) {}

    val trimmedContentAddress = new UnusedAddress(failMethod):
      override def toContent: Content =
        writtenContent

    val destinationAddress = new UnusedAddress(failMethod) {}

    val destinationPath = new UnusedPath(failMethod) {}
    val trimmedDestinationAddress = new UnusedAddress(failMethod):
      override def toPath: Path =
        destinationPath

    val nextAddress = new UnusedAddress(failMethod) {}

    val updatedRootElement = new UnusedElement(failMethod) {}

    val rootElement = new UnusedElement(failMethod):
      override def write(destination: Path, content: Content): Either[String, Element] =
        assert(destination.equals(destinationPath))
        assert(content.equals(writtenContent))
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

    val originPath = new UnusedPath(failMethod) {}

    val trimmedOriginAddress = new UnusedAddress(failMethod):
      override def toPath: Path =
        originPath

    val nextAddress = new UnusedAddress(failMethod) {}

    val retrievedContent = new UnusedContent(failMethod):
      override def toAddress: Address =
        contentAddress

    val rootElement = new UnusedElement(failMethod):
      override def read(origin: Path): Either[String, Content] =
        assert(origin.equals(originPath))
        Right(retrievedContent)

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

