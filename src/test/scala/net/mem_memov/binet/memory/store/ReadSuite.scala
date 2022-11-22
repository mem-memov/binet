package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Address, Block, Store}
import net.mem_memov.binet.memory.specific.Store.given

class ReadSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)
  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)
  val b4 = UnsignedByte.fromInt(4)

  class FactoryStub
  given factoryStub: FactoryStub = new FactoryStub

  class AddressStub
  given addressStub: AddressStub = new AddressStub

  test("Store can be read at the first index") {

    given general.factory.MakeAddress[FactoryStub, AddressStub] with
      override def f(indices: List[UnsignedByte]): AddressStub =
        assert(indices == List(b3, b1))
        addressStub

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b4))
    ))

    val result = store.read(b0)

    assert(result.equals(addressStub))
  }

  test("Store can be read at the last index") {

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b4))
    ))

    given general.factory.MakeAddress[FactoryStub, AddressStub] with
      override def f(indices: List[UnsignedByte]): AddressStub =
        assert(indices == List(b4, b2))
        addressStub

    val result = store.read(b1)

    assert(result.equals(addressStub))
  }
