package net.mem_memov.binet.specific.element.specific.reader

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.specific.Reader
import net.mem_memov.binet.memory.specific.element.specific.Reader.given

class ReadStockSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class FactoryStub
  given factoryStub: FactoryStub = new FactoryStub

  class StockStub
  given stockStub: StockStub = new StockStub

  class AddressStub
  given addressStub: AddressStub = new AddressStub

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  class PathStub
  given pathStub: PathStub = new PathStub

  test("Reader retrieves content from an available stock") {

    given general.factory.EmptyStock[FactoryStub, StockStub] with
      override def f(): StockStub =
        fail("unexpected")

    given general.stock.Read[StockStub, ContentStub, PathStub] with
      override def f(stock: StockStub, index: general.UnsignedByte, origin: PathStub): Either[String, ContentStub] =
        assert(stock.equals(stockStub))
        assert(index == b5)
        assert(origin.equals(pathStub))
        Right(contentStub)

    val reader = new Reader

    for {
      result <- reader.readStock(
        Some(stockStub),
        general.Split(b5, pathStub)
      )
    } yield assert(result.equals(contentStub))
  }

  test("Reader retrieves content from an empty stock") {

    given general.factory.EmptyStock[FactoryStub, StockStub] with
      override def f(): StockStub =
        stockStub

    given general.stock.Read[StockStub, ContentStub, PathStub] with
      override def f(stock: StockStub, index: general.UnsignedByte, origin: PathStub): Either[String, ContentStub] =
        assert(stock.equals(stockStub))
        assert(index == b5)
        assert(origin.equals(pathStub))
        Right(contentStub)

    val reader = new Reader

    for {
      result <- reader.readStock(
        None,
        general.Split(b5, pathStub)
      )
    } yield assert(result.equals(contentStub))
  }