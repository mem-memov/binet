package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.{Address, Element, Inventory}
import net.mem_memov.binet.memory.specific.Inventory.given

class AppendSuite extends munit.FunSuite:

  class ArgumentStub
  given ArgumentStub = new ArgumentStub

  class ContentStub
  class PathStub
  
  test("") {

    val originalInventoryNextAddress = Address(List.empty)
    val incrementedInventoryNextAddress = Address(List.empty)

    val originalInventoryRootElement = Element(None, None)
    val updatedInventoryRootElement = Element(None, None)

    val contentAddress = Address(List.empty)
    val trimmedContentAddress = Address(List.empty)

    val contentStub = new ContentStub
    val inventoryNextPathStub = new PathStub

    given specific.inventory.general.argument.CheckAndTrimPermissive[ArgumentStub, Address] with
      override def f(argument: ArgumentStub, next: Address, address: Address): Either[String, Address] =
        assert(next.equals(originalInventoryNextAddress))
        assert(address.equals(contentAddress))
        Right(trimmedContentAddress)

    given general.address.ToContent[Address, ContentStub] with
      override def f(address: Address): ContentStub =
        assert(address.equals(contentAddress))
        contentStub

    given general.address.ToPath[Address, PathStub] with
      override def f(address: Address): PathStub =
        assert(address.equals(originalInventoryNextAddress))
        inventoryNextPathStub

    given general.element.Write[Element, PathStub, ContentStub] with
      override def f(element: Element, destination: PathStub, content: ContentStub): Either[String, Element] =
        assert(element.equals(originalInventoryRootElement))
        assert(destination.equals(inventoryNextPathStub))
        assert(content.equals(contentStub))
        Right(updatedInventoryRootElement)

    given general.address.Increment[Address] with
      override def f(address: Address): Address =
        assert(address.equals(originalInventoryNextAddress))
        incrementedInventoryNextAddress

    val inventory = new Inventory(originalInventoryNextAddress, originalInventoryRootElement)

    for {
      result <- inventory.append(contentAddress)
    } yield
      assert(result.next.equals(incrementedInventoryNextAddress))
      assert(result.root.equals(updatedInventoryRootElement))
  }
