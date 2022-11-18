package net.mem_memov.binet.specific.element

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.Element
import net.mem_memov.binet.memory.specific.Element.given

class ReadSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class PathStub
  given originPathStub: PathStub = new PathStub
  given restPathStub: PathStub = new PathStub

  class StockReaderStub
  given stockReaderStub: StockReaderStub = new StockReaderStub

  class StoreReaderStub
  given storeReaderStub: StoreReaderStub = new StoreReaderStub

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  class StockStub
  given stockStub: StockStub = new StockStub

  class StoreStub
  given storeStub: StoreStub = new StoreStub

  test("Element retrieves content from store") {

    given general.path.Shorten[PathStub] with
      override def f(path: PathStub): Either[String, general.Split[PathStub]] =
        Right(general.Split(b5, restPathStub))

    given general.path.IsEmpty[PathStub] with
      override def f(path: PathStub): Boolean =
        true

    given specific.element.general.reader.ReadStock[StockReaderStub, ContentStub, PathStub, specific.Stock] with
      override def f(reader: StockReaderStub, stockOption: Option[StockStub], pathSplit: PathStub): Either[String, ContentStub] =
        fail("unexpected")

    given specific.element.general.reader.ReadStore[StoreReaderStub, ContentStub, PathStub, specific.Store] with
      override def f(reader: StoreReaderStub, storeOption: Option[StoreStub], pathSplit: PathStub): ContentStub =
        contentStub

    val element = Element(Some(specific.Store(Vector())), None)

    for {
      result <- element.read(originPathStub)
    } yield()
  }
