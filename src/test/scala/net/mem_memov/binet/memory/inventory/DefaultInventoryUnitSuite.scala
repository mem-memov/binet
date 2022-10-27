package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.factory.DefaultFactory
import net.mem_memov.binet.memory.factory.defaultFactory.*
import net.mem_memov.binet.memory.inventory.defaultInventory.*
import net.mem_memov.binet.memory.live.DefaultInventory

class DefaultInventoryUnitSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given AddressFactory = new UnusedAddressFactory(failMethod) {}

  test("Inventory appends content") {

    val addressEmpty = new UnusedAddress(failMethod) {}

    val trimmedContentAddress = new UnusedAddress(failMethod) {}

    lazy val contentAddress = new UnusedAddress(failMethod):
      override def isEmpty: Boolean =
        false
      override def trimBig: Address =
        trimmedContentAddress

    val newNextAddress = new UnusedAddress(failMethod) {}

    val nextAddress = new UnusedAddress(failMethod):
      override def canCompare(that: Address): Boolean =
        assert(that.equals(contentAddress))
        true
      override def isLessOrEqual(that: Address): Boolean =
        assert(that.equals(trimmedContentAddress))
        false
      override def increment: Address =
        newNextAddress

    val updatedRootElement = new UnusedElement(failMethod) {}

    val rootElement = new UnusedElement(failMethod):
      override def write(destination: Address, content: Address): Either[String, Element] =
        assert(destination.equals(nextAddress))
        assert(content.equals(trimmedContentAddress))
        Right(updatedRootElement)

    given AddressFactory = new UnusedAddressFactory(failMethod) :
      override lazy val zeroAddress: Address = addressEmpty

    val argumentChecking = ArgumentChecking()

    val inventory = DefaultInventory(nextAddress, rootElement, argumentChecking)

    for {
      result <- inventory.append(contentAddress)
    } yield assert(result.next.equals(newNextAddress) && result.root.equals(updatedRootElement))
  }

  test("Inventory appends first content") {

    val addressEmpty = new UnusedAddress(failMethod) {}

    val trimmedContentAddress = new UnusedAddress(failMethod) {}

    lazy val contentAddress = new UnusedAddress(failMethod) :
      override def isEmpty: Boolean =
        false

      override def trimBig: Address =
        trimmedContentAddress

    val newNextAddress = new UnusedAddress(failMethod) {}

    val nextAddress = new UnusedAddress(failMethod) :
      override def canCompare(that: Address): Boolean =
        assert(that.equals(contentAddress))
        true
      override def isLessOrEqual(that: Address): Boolean =
        assert(that.equals(trimmedContentAddress))
        true
      override def isEqual(that: Address): Boolean =
        assert(that.equals(addressEmpty))
        true
      override def increment: Address =
        newNextAddress

    val updatedRootElement = new UnusedElement(failMethod) {}

    val rootElement = new UnusedElement(failMethod) :
      override def write(destination: Address, content: Address): Either[String, Element] =
        assert(destination.equals(nextAddress))
        assert(content.equals(trimmedContentAddress))
        Right(updatedRootElement)

    given AddressFactory = new UnusedAddressFactory(failMethod) :
      override lazy val zeroAddress: Address = addressEmpty

    val argumentChecking = ArgumentChecking()

    val inventory = DefaultInventory(nextAddress, rootElement, argumentChecking)

    for {
      result <- inventory.append(contentAddress)
    } yield assert(result.next.equals(newNextAddress) && result.root.equals(updatedRootElement))
  }


