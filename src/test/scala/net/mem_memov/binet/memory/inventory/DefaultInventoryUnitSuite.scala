package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.DefaultFactory
import net.mem_memov.binet.memory.factory.defaultFactory._

class DefaultInventoryUnitSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given AddressFactory = new UnusedAddressFactory(failMethod) {}

  test("Inventory appends content") {

    val newNextAddress = new UnusedAddress(failMethod) {}

    val nextAddress = new UnusedAddress(failMethod):
      override def canCompare(that: Address): Boolean =
        true
      override def increment: Address = newNextAddress

    val trimmedContentAddress = new UnusedAddress(failMethod):
      override def isGreaterOrEqual(that: Address): Boolean =
        false

    lazy val contentAddress = new UnusedAddress(failMethod):
      private[memory]
      override def isEmpty = false
      private[memory]
      override def trimBig = trimmedContentAddress

    val updatedRootElement = new UnusedElement(failMethod) {}

    val rootElement = new UnusedElement(failMethod):
      override def write(destination: Address, content: Address): Either[String, Element] =
        assert(destination.equals(nextAddress))
        assert(content.equals(trimmedContentAddress))
        Right(updatedRootElement)

    val inventory = DefaultInventory(nextAddress, rootElement)

    for {
      result <- inventory.append(contentAddress)
    } yield assert(result.next.equals(newNextAddress) && result.root.equals(updatedRootElement))
  }

  test("Inventory appends first content") {

    val addressEmpty = new UnusedAddress(failMethod) {}

    val newNextAddress = new UnusedAddress(failMethod) {}

    val trimmedContentAddress: Address = new UnusedAddress(failMethod) :
      override def isGreaterOrEqual(that: Address): Boolean =
//        assert(that.equals(nextAddress))
        true

    val contentAddress = new UnusedAddress(failMethod) :
      override def isEmpty = false
      override def trimBig: Address = trimmedContentAddress

    val nextAddress = new UnusedAddress(failMethod):
      override def canCompare(that: Address): Boolean =
        assert(that.equals(contentAddress))
        true
      override def increment: Address = newNextAddress
      override def isEqual(that: Address): Boolean =
        assert(that.equals(addressEmpty))
        true

    val updatedRootElement = new UnusedElement(failMethod) {}

    val rootElement = new UnusedElement(failMethod) :
      override def write(destination: Address, content: Address): Either[String, Element] =
        assert(destination.equals(nextAddress))
        assert(content.equals(trimmedContentAddress))
        Right(updatedRootElement)

    given AddressFactory = new UnusedAddressFactory(failMethod):
      override lazy val zeroAddress: Address = addressEmpty

    val inventory = DefaultInventory(nextAddress, rootElement)

    for {
      result <- inventory.append(contentAddress)
    } yield assert(result.next.equals(newNextAddress) && result.root.equals(updatedRootElement))
  }


