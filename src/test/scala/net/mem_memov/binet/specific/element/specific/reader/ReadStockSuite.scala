package net.mem_memov.binet.specific.element.specific.reader

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.specific.Reader
import net.mem_memov.binet.memory.specific.element.specific.Reader.given

class ReadStockSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class FactoryMock
  given factoryMock: FactoryMock = new FactoryMock

  class StockMock
  given stockMock: StockMock = new StockMock

  class AddressMock
  given addressMock: AddressMock = new AddressMock

  class ContentMock
  given contentMock: ContentMock = new ContentMock

  class PathMock
  given pathMock: PathMock = new PathMock

  test("Reader retrieves content from an available stock") {

    given general.factory.EmptyStock[FactoryMock, StockMock] with
      override def f(): StockMock =
        fail("unexpected")

    given general.stock.Read[StockMock, ContentMock, PathMock] with
      override def f(stock: StockMock, index: general.UnsignedByte, origin: PathMock): Either[String, ContentMock] =
        assert(stock.equals(stockMock))
        assert(index == b5)
        assert(origin.equals(pathMock))
        Right(contentMock)

    val reader = new Reader

    for {
      result <- reader.readStock(
        Some(stockMock),
        general.Split(b5, pathMock)
      )
    } yield assert(result.equals(contentMock))
  }

  test("Reader retrieves content from an empty stock") {

    given general.factory.EmptyStock[FactoryMock, StockMock] with
      override def f(): StockMock =
        stockMock

    given general.stock.Read[StockMock, ContentMock, PathMock] with
      override def f(stock: StockMock, index: general.UnsignedByte, origin: PathMock): Either[String, ContentMock] =
        assert(stock.equals(stockMock))
        assert(index == b5)
        assert(origin.equals(pathMock))
        Right(contentMock)

    val reader = new Reader

    for {
      result <- reader.readStock(
        None,
        general.Split(b5, pathMock)
      )
    } yield assert(result.equals(contentMock))
  }