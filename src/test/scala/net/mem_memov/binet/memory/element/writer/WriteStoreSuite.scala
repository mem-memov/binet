package net.mem_memov.binet.memory.element.writer

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.element.specific.Writer
import net.mem_memov.binet.memory.specific.element.specific.Writer.given

class WriteStoreSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class StoreStub
  given originalStoreStub: StoreStub = new StoreStub
  given modifiedStoreStub: StoreStub = new StoreStub

  class PathStub
  given restPathStub: PathStub = new PathStub

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  class FactoryStub
  given factoryStub: FactoryStub = new FactoryStub

  test("Writer puts content into an available store") {

    val writer = new Writer

    given general.factory.EmptyStore[FactoryStub, StoreStub] with
      override def f(): StoreStub =
        fail("unexpected")

    given general.store.Write[StoreStub, ContentStub] with
      override def f(store: StoreStub, destination: UnsignedByte, content: ContentStub): StoreStub =
        assert(store.equals(originalStoreStub))
        assert(destination == b5)
        assert(content.equals(contentStub))
        modifiedStoreStub

    val result = writer.writeStore(
      Some(originalStoreStub),
      general.Split(b5, restPathStub),
      contentStub
    )

    assert(result.equals(modifiedStoreStub))
  }
