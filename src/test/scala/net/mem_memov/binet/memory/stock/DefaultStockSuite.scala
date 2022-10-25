package net.mem_memov.binet.memory.stock

import net.mem_memov.binet.memory.factory.defaultFactory._
import net.mem_memov.binet.memory._

class DefaultStockSuite extends munit.FunSuite:

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

    val updatedStock = new UnusedStock(failMethod) {}

    def updateWithElements(updatedElements: Vector[Element]): Stock =
      assert(updatedElements(0) == updatedElement)
      updatedStock

    for {
      result <- DefaultStock.write(index, destinationAddress, contentAddress, Vector(element), updateWithElements)
    } yield assert(result == updatedStock)
  }
