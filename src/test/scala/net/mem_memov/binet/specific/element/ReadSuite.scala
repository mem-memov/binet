package net.mem_memov.binet.specific.element

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.Split
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.{Element, Stock, Store}
import net.mem_memov.binet.memory.specific.Element.given

class ReadSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class PathStub
  given originPathStub: PathStub = new PathStub
  given restPathStub: PathStub = new PathStub

  class ReaderStub
  given readerStub: ReaderStub = new ReaderStub

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  test("Element retrieves content from store") {

    given elementStore: specific.Store = specific.Store(Vector())

    given general.path.Shorten[PathStub] with
      override def f(path: PathStub): Either[String, general.Split[PathStub]] =
        assert(path.equals(originPathStub))
        Right(general.Split(b5, restPathStub))

    given general.path.IsEmpty[PathStub] with
      override def f(path: PathStub): Boolean =
        assert(path.equals(restPathStub))
        true

    given specific.element.general.reader.ReadStock[ReaderStub, ContentStub, PathStub, specific.Stock] with
      override def f(reader: ReaderStub, stockOption: Option[Stock], pathSplit: Split[PathStub]): Either[String, ContentStub] =
        fail("unexpected")

    given specific.element.general.reader.ReadStore[ReaderStub, ContentStub, PathStub, specific.Store] with
      override def f(reader: ReaderStub, storeOption: Option[Store], pathSplit: Split[PathStub]): ContentStub =
        assert(reader.equals(readerStub))
        assert(storeOption.contains(elementStore))
        assert(pathSplit.index == b5, pathSplit.rest.equals(restPathStub))
        contentStub

    val element = Element(Some(elementStore), None)

    for {
      result <- element.read(originPathStub)
    } yield assert(result.equals(contentStub))
  }

  test("Element retrieves content from stock") {

    given elementStock: specific.Stock = specific.Stock(Vector())

    given general.path.Shorten[PathStub] with
      override def f(path: PathStub): Either[String, general.Split[PathStub]] =
        assert(path.equals(originPathStub))
        Right(general.Split(b5, restPathStub))

    given general.path.IsEmpty[PathStub] with
      override def f(path: PathStub): Boolean =
        assert(path.equals(restPathStub))
        true

    given specific.element.general.reader.ReadStock[ReaderStub, ContentStub, PathStub, specific.Stock] with
      override def f(reader: ReaderStub, stockOption: Option[Stock], pathSplit: Split[PathStub]): Either[String, ContentStub] =
        assert(reader.equals(readerStub))
        assert(stockOption.contains(elementStock))
        assert(pathSplit.index == b5, pathSplit.rest.equals(restPathStub))
        Right(contentStub)

    given specific.element.general.reader.ReadStore[ReaderStub, ContentStub, PathStub, specific.Store] with
      override def f(reader: ReaderStub, storeOption: Option[Store], pathSplit: Split[PathStub]): ContentStub =
        fail("unexpected")

    val element = Element(None, Some(elementStock))

    for {
      result <- element.read(originPathStub)
    } yield assert(result.equals(contentStub))
  }
