package net.mem_memov.binet.specific.stock

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Element, Stock}
import net.mem_memov.binet.memory.specific.Stock.given

class WriteSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  class PathStub
  given destinationPathStub: PathStub = new PathStub
  
  test("Stock writes some content") {

    val originalElement = Element(None, None)
    val modifiedElement = Element(None, None)

    given general.element.Write[Element, PathStub, ContentStub] with
      override def f(element: Element, destination: PathStub, content: ContentStub): Either[String, Element] =
        assert(element.equals(originalElement))
        assert(destination.equals(destinationPathStub))
        assert(content.equals(contentStub))
        Right(modifiedElement)

    val stock = Stock(Vector(originalElement))

    for {
      result <- stock.write(b0, destinationPathStub, contentStub)
    } yield assert(result.elements.head.equals(modifiedElement))
  }
