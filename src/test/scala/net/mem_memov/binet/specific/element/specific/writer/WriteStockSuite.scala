package net.mem_memov.binet.specific.element.specific.writer

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.element.specific.Writer
import net.mem_memov.binet.memory.specific.element.specific.Writer.given

class WriteStockSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class StockStub
  given originalStockStub: StockStub = new StockStub
  given modifiedStockStub: StockStub = new StockStub

  class PathStub
  given restPathStub: PathStub = new PathStub

  class FactoryStub
  given factoryStub: FactoryStub = new FactoryStub

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  test("Writer puts content into an available stock") {

    given general.factory.EmptyStock[FactoryStub, StockStub] with
      override def f(): StockStub =
        fail("unexpected")

    given general.stock.Write[StockStub, ContentStub, PathStub] with
      override def f(stock: StockStub, index: general.UnsignedByte, destination: PathStub, content: ContentStub): Either[String, StockStub] =
        assert(stock.equals(originalStockStub))
        assert(index == b5)
        assert(destination.equals(restPathStub))
        Right(modifiedStockStub)

    val writer = new Writer

    for {
      result <- writer.writeStock(
        Some(originalStockStub),
        general.Split(b5, restPathStub),
        contentStub
      )
    } yield assert(result.equals(modifiedStockStub))
  }

  test("Writer puts content into an empty stock") {

    given general.factory.EmptyStock[FactoryStub, StockStub] with
      override def f(): StockStub =
        originalStockStub

    given general.stock.Write[StockStub, ContentStub, PathStub] with
      override def f(stock: StockStub, index: general.UnsignedByte, destination: PathStub, content: ContentStub): Either[String, StockStub] =
        assert(stock.equals(originalStockStub))
        assert(index == b5)
        assert(destination.equals(restPathStub))
        Right(modifiedStockStub)

    val writer = new Writer

    for {
      result <- writer.writeStock(
        None,
        general.Split(b5, restPathStub),
        contentStub
      )
    } yield assert(result.equals(modifiedStockStub))
  }
