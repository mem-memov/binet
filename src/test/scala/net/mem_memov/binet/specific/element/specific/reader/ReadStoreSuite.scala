package net.mem_memov.binet.specific.element.specific.reader

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.element.specific.Reader
import net.mem_memov.binet.memory.specific.element.specific.Reader.given

class ReadStoreSuite extends munit.FunSuite:

  val b5 = general.UnsignedByte.fromInt(5)

  class FactoryMock
  given factoryMock: FactoryMock = new FactoryMock

  class StoreMock
  given storeMock: StoreMock = new StoreMock

  class AddressMock
  given addressMock: AddressMock = new AddressMock

  class ContentMock
  given contentMock: ContentMock = new ContentMock

  class PathMock
  given pathMock: PathMock = new PathMock

  test("Reader retrieves content from an available store") {

    given general.factory.EmptyStore[FactoryMock, StoreMock] with
      override def f(): StoreMock =
        fail("unexpected")

    given general.store.Read[StoreMock, AddressMock] with
      override def f(store: StoreMock, origin: general.UnsignedByte): AddressMock =
        assert(store.equals(storeMock))
        assert(origin == b5)
        addressMock

    given general.address.ToContent[AddressMock, ContentMock] with
      override def f(address: AddressMock): ContentMock =
        assert(address.equals(addressMock))
        contentMock

    val reader = new Reader

    val result = reader.readStore(
      Some(storeMock),
      general.Split(b5, pathMock)
    )

    assert(result.equals(contentMock))
  }

  test("Reader retrieves content from the empty store") {

    given general.factory.EmptyStore[FactoryMock, StoreMock] with
      override def f(): StoreMock =
        storeMock

    given general.store.Read[StoreMock, AddressMock] with
      override def f(store: StoreMock, origin: general.UnsignedByte): AddressMock =
        assert(store.equals(storeMock))
        assert(origin == b5)
        addressMock

    given general.address.ToContent[AddressMock, ContentMock] with
      override def f(address: AddressMock): ContentMock =
        assert(address.equals(addressMock))
        contentMock

    val reader = new Reader

    val result = reader.readStore(
      None,
      general.Split(b5, pathMock)
    )

    assert(result.equals(contentMock))
  }
