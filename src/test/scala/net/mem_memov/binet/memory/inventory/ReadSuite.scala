package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.{Address, Element, Inventory}
import net.mem_memov.binet.memory.specific.Inventory.given

class ReadSuite extends munit.FunSuite:

  class ArgumentStub
  given ArgumentStub = new ArgumentStub

  class ContentStub
  class PathStub

  test("Inventory reads content at path") {

    val inventoryNextAddress = Address(List.empty)
    val inventoryRootElement = Element(None, None)

    val originAddress = Address(List.empty)
    val trimmedOriginAddress = Address(List.empty)

    val contentStub = new ContentStub
    val pathStub = new PathStub

    val contentAddress = Address(List.empty)
    val trimmedContentAddress = Address(List.empty)

    given specific.inventory.general.argument.CheckAndTrimRestrictive[ArgumentStub, Address] with
      override def f(next: Address, address: Address): Either[String, Address] =
        assert(next.equals(inventoryNextAddress))
        if address.equals(originAddress) then
          Right(trimmedOriginAddress)
        else
          fail("unexpected")

    given general.address.ToPath[Address, PathStub] with
      override def f(address: Address): PathStub =
        assert(address.equals(trimmedOriginAddress))
        pathStub

    given general.element.Read[Element, PathStub, ContentStub] with
      override def f(element: Element, origin: PathStub): Either[String, ContentStub] =
        assert(origin.equals(pathStub))
        Right(contentStub)

    given general.content.ToAddress[ContentStub, Address] with
      override def f(content: ContentStub): Address =
        assert(content.equals(contentStub))
        contentAddress

    given general.address.TrimBig[Address] with
      override def f(address: Address): Address =
        assert(address.equals(contentAddress))
        trimmedContentAddress

    val inventory = new Inventory(inventoryNextAddress, inventoryRootElement)

    for {
      result <- inventory.read(originAddress)
    } yield assert(result.equals(trimmedContentAddress))
  }
