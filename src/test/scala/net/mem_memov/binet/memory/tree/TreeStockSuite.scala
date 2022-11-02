package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.tree.treeFactory._

class TreeStockSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  test("Stock writes address at an index in its store") {

    val index = UnsignedByte.fromInt(0)

    val destinationAddress = new UnusedPath(failMethod) {}
    val contentAddress = new UnusedContent(failMethod) {}

    val updatedElement = new UnusedElement(failMethod) {}

    val element = new UnusedElement(failMethod):
      override def write(destination: Path, content: Content): Either[String, Element] =
        assert(destination.equals(destinationAddress))
        assert(content.equals(contentAddress))
        Right(updatedElement)

    val stock = TreeStock(Vector(element))

    for {
      result <- stock.write(index, destinationAddress, contentAddress)
    } yield assert(result.elements(0) == updatedElement)
  }

  test("Stock reads address at index") {

    val elementIndex = UnsignedByte.fromInt(0)

    val originPath = new UnusedPath(failMethod) {}
    val retrievedContent = new UnusedContent(failMethod) {}

    val element = new UnusedElement(failMethod):
      override def read(origin: Path): Either[String, Content] =
        assert(origin.equals(originPath))
        Right(retrievedContent)

    val stock = TreeStock(Vector(element))

    for {
      result <- stock.read(elementIndex, originPath)
    } yield assert(result == retrievedContent)
  }
