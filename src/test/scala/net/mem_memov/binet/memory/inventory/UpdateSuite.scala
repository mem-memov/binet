package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.{Address, Element, Inventory}
import net.mem_memov.binet.memory.specific.Inventory.given

class UpdateSuite extends munit.FunSuite:

  class ArgumentStub
  given ArgumentStub = new ArgumentStub

  class ContentStub
  class PathStub

  test("Inventory updates path with content") {

    val originalInventoryNextAddress = Address(List.empty)

    val originalInventoryRootElement = Element(None, None)
    val updatedInventoryRootElement = Element(None, None)

    val contentAddress = Address(List.empty)
    val trimmedContentAddress = Address(List.empty)

    val pathAddress = Address(List.empty)
    val trimmedPathAddress = Address(List.empty)

    val contentStub = new ContentStub
    val pathStub = new PathStub

    given specific.inventory.general.argument.CheckAndTrimRestrictive[ArgumentStub, Address] with
      override def f(argument: ArgumentStub, next: Address, address: Address): Either[String, Address] =
        assert(next.equals(originalInventoryNextAddress))
        if address.equals(contentAddress) then
          Right(trimmedContentAddress)
        else if address.equals(pathAddress) then
          Right(trimmedPathAddress)
        else
          fail("unexpected")

    given general.address.ToContent[Address, ContentStub] with
      override def f(address: Address): ContentStub =
        assert(address.equals(trimmedContentAddress))
        contentStub

    given general.address.ToPath[Address, PathStub] with
      override def f(address: Address): PathStub =
        assert(address.equals(trimmedPathAddress))
        pathStub

    given general.element.Write[Element, PathStub, ContentStub] with
      override def f(element: Element, destination: PathStub, content: ContentStub): Either[String, Element] =
        assert(destination.equals(pathStub))
        assert(content.equals(contentStub))
        Right(updatedInventoryRootElement)

    val inventory = new Inventory(originalInventoryNextAddress, originalInventoryRootElement)

    for {
      result <- inventory.update(pathAddress, contentAddress)
    } yield
      assert(result.next.equals(originalInventoryNextAddress))
      assert(result.root.equals(updatedInventoryRootElement))
  }
