package net.mem_memov.binet.specific.stock

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Content, Element, Path, Stock}
import net.mem_memov.binet.memory.specific.Stock.given

class ReadSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  class PathStub
  given originPathStub: PathStub = new PathStub

  test("Stock retrieves some content") {

    val stockElement = Element(None, None)

    given general.element.Read[Element, PathStub, ContentStub] with
      override def f(element: Element, origin: PathStub): Either[String, ContentStub] =
        assert(element.equals(stockElement))
        assert(origin.equals(originPathStub))
        Right(contentStub)

    val stock = Stock(Vector(stockElement))

    for {
      result <- stock.read(b0, originPathStub)
    } yield assert(result == contentStub)
  }
