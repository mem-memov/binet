package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._

class TreeStockSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  test("Stock writes address at an index in its store") {

    val index = UnsignedByte.fromInt(0)

    val destinationAddress = new UnusedAddress(failMethod) {}
    val contentAddress = new UnusedAddress(failMethod) {}

    val updatedElement = new UnusedElement(failMethod) {}

    val element = new UnusedElement(failMethod):
      override def write(destination: Address, content: Address): Either[String, Element] =
        assert(destination.equals(destinationAddress))
        assert(content.equals(contentAddress))
        Right(updatedElement)

    val stock = TreeStock(Vector(element))

    for {
      result <- stock.write(index, destinationAddress, contentAddress)
    } yield assert(result.elements(0) == updatedElement)
  }

  test("Stock reads address at index") {

    val index = UnsignedByte.fromInt(0)

    val originAddress = new UnusedAddress(failMethod) {}
    val contentAddress = new UnusedAddress(failMethod) {}

    val element = new UnusedElement(failMethod):
      override def read(origin: Address): Either[String, Address] =
        assert(origin.equals(originAddress))
        Right(contentAddress)

    val stock = TreeStock(Vector(element))

    for {
      result <- stock.read(index, originAddress)
    } yield assert(result == contentAddress)
  }
