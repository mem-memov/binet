package net.mem_memov.binet.specific.stock

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Content, Element, Path, Stock}
import net.mem_memov.binet.memory.specific.Stock.given

class ReadSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)

  test("Stock ") {

    val content = Content(Vector())

    given general.element.Read[Element, Path, Content] with
      override def f(element: Element, origin: Path): Either[String, Content] =
        Right(content)

    val path = Path(Vector())

    val stock = Stock(Vector(Element.emptyElement))

    for {
      result <- stock.read(b0, path)
    } yield ()
  }
