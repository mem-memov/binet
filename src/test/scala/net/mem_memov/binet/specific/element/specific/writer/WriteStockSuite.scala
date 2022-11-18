package net.mem_memov.binet.specific.element.specific.writer

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.element.specific.Writer
import net.mem_memov.binet.memory.specific.element.specific.Writer.given

class WriteStockSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class StockMock
  given originalStockMock: StockMock = new StockMock
  given modifiedStockMock: StockMock = new StockMock

  class PathMock
  given restPathMock: PathMock = new PathMock

  class FactoryMock
  given factoryMock: FactoryMock = new FactoryMock

  class ContentMock
  given contentMock: ContentMock = new ContentMock

  test("Writer puts content into an available stock") {

    given general.factory.EmptyStock[FactoryMock, StockMock] with
      override def f(): StockMock =
        fail("unexpected")

    given general.stock.Write[StockMock, ContentMock, PathMock] with
      override def f(stock: StockMock, index: UnsignedByte, destination: PathMock, content: ContentMock): Either[String, StockMock] =
        assert(stock.equals(originalStockMock))
        assert(index == b5)
        assert(destination.equals(restPathMock))
        Right(modifiedStockMock)

    val writer = new Writer

    for {
      result <- writer.writeStock(
        Some(originalStockMock),
        general.Split(b5, restPathMock),
        contentMock
      )
    } yield assert(result.equals(modifiedStockMock))
  }

  test("Writer puts content into an empty stock") {

    given general.factory.EmptyStock[FactoryMock, StockMock] with
      override def f(): StockMock =
        originalStockMock

    given general.stock.Write[StockMock, ContentMock, PathMock] with
      override def f(stock: StockMock, index: UnsignedByte, destination: PathMock, content: ContentMock): Either[String, StockMock] =
        assert(stock.equals(originalStockMock))
        assert(index == b5)
        assert(destination.equals(restPathMock))
        Right(modifiedStockMock)

    val writer = new Writer

    for {
      result <- writer.writeStock(
        None,
        general.Split(b5, restPathMock),
        contentMock
      )
    } yield assert(result.equals(modifiedStockMock))
  }
