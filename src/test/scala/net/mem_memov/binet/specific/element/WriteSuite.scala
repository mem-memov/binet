package net.mem_memov.binet.specific.element

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.{Element, Stock, Store}
import net.mem_memov.binet.memory.specific.Element.given

class WriteSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class PathStub
  given destinationPathStub: PathStub = new PathStub
  given restPathStub: PathStub = new PathStub

  class WriterStub
  given writerStub: WriterStub = new WriterStub

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  test("Element writes some content into its store") {

    val originalStore: specific.Store = specific.Store(Vector())
    val modifiedStore: specific.Store = specific.Store(Vector())

    given general.path.Shorten[PathStub] with
      override def f(path: PathStub): Either[String, general.Split[PathStub]] =
        assert(path.equals(destinationPathStub))
        Right(general.Split(b5, restPathStub))

    given general.path.IsEmpty[PathStub] with
      override def f(path: PathStub): Boolean =
        assert(path.equals(restPathStub))
        true

    given specific.element.general.writer.WriteStock[WriterStub, ContentStub, PathStub, specific.Stock] with
      override def f(writer: WriterStub, stockOption: Option[Stock], pathSplit: general.Split[PathStub], content: ContentStub): Either[String, Stock] =
        fail("unexpected")

    given specific.element.general.writer.WriteStore[WriterStub, ContentStub, PathStub, specific.Store] with
      override def f(writer: WriterStub, storeOption: Option[Store], pathSplit: general.Split[PathStub], content: ContentStub): Store =
        assert(writer.equals(writerStub))
        assert(storeOption.contains(originalStore))
        assert(pathSplit.index == b5, pathSplit.rest.equals(restPathStub))
        modifiedStore

    val element = Element(Some(originalStore), None)

    for {
      result <- element.write(destinationPathStub, contentStub)
    } yield assert(result.storeOption.contains(modifiedStore))
  }

  test("Element writes some content into its stock") {

    val originalStock: specific.Stock = specific.Stock(Vector())
    val modifiedStock: specific.Stock = specific.Stock(Vector())

    given general.path.Shorten[PathStub] with
      override def f(path: PathStub): Either[String, general.Split[PathStub]] =
        assert(path.equals(destinationPathStub))
        Right(general.Split(b5, restPathStub))

    given general.path.IsEmpty[PathStub] with
      override def f(path: PathStub): Boolean =
        assert(path.equals(restPathStub))
        false

    given specific.element.general.writer.WriteStock[WriterStub, ContentStub, PathStub, specific.Stock] with
      override def f(writer: WriterStub, stockOption: Option[Stock], pathSplit: general.Split[PathStub], content: ContentStub): Either[String, Stock] =
        assert(writer.equals(writerStub))
        assert(stockOption.contains(originalStock))
        assert(pathSplit.index == b5, pathSplit.rest.equals(restPathStub))
        Right(modifiedStock)

    given specific.element.general.writer.WriteStore[WriterStub, ContentStub, PathStub, specific.Store] with
      override def f(writer: WriterStub, storeOption: Option[Store], pathSplit: general.Split[PathStub], content: ContentStub): Store =
        fail("unexpected")

    val element = Element(None, Some(originalStock))

    for {
      result <- element.write(destinationPathStub, contentStub)
    } yield assert(result.stockOption.contains(modifiedStock))
  }
