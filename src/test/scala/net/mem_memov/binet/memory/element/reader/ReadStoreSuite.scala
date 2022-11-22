package net.mem_memov.binet.memory.element.specific.reader

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.specific.Reader
import net.mem_memov.binet.memory.specific.element.specific.Reader.given

class ReadStoreSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class FactoryStub
  given factoryStub: FactoryStub = new FactoryStub

  class StoreStub
  given storeStub: StoreStub = new StoreStub

  class AddressStub
  given addressStub: AddressStub = new AddressStub

  class ContentStub
  given contentStub: ContentStub = new ContentStub

  class PathStub
  given pathStub: PathStub = new PathStub

  test("Reader retrieves content from an available store") {

    given general.factory.EmptyStore[FactoryStub, StoreStub] with
      override def f(): StoreStub =
        fail("unexpected")

    given general.store.Read[StoreStub, AddressStub] with
      override def f(store: StoreStub, origin: general.UnsignedByte): AddressStub =
        assert(store.equals(storeStub))
        assert(origin == b5)
        addressStub

    given general.address.ToContent[AddressStub, ContentStub] with
      override def f(address: AddressStub): ContentStub =
        assert(address.equals(addressStub))
        contentStub

    val reader = new Reader

    val result = reader.readStore(
      Some(storeStub),
      general.Split(b5, pathStub)
    )

    assert(result.equals(contentStub))
  }

  test("Reader retrieves content from the empty store") {

    given general.factory.EmptyStore[FactoryStub, StoreStub] with
      override def f(): StoreStub =
        storeStub

    given general.store.Read[StoreStub, AddressStub] with
      override def f(store: StoreStub, origin: general.UnsignedByte): AddressStub =
        assert(store.equals(storeStub))
        assert(origin == b5)
        addressStub

    given general.address.ToContent[AddressStub, ContentStub] with
      override def f(address: AddressStub): ContentStub =
        assert(address.equals(addressStub))
        contentStub

    val reader = new Reader

    val result = reader.readStore(
      None,
      general.Split(b5, pathStub)
    )

    assert(result.equals(contentStub))
  }
