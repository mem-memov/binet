package net.mem_memov.binet.specific.store

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Address, Block, Store}
import net.mem_memov.binet.memory.specific.Store.given

class ReadSuite extends munit.FunSuite:

  test("Store can be read at the first index") {

    val store = Store(Vector(
      Block(Vector(UnsignedByte.fromInt(1), UnsignedByte.fromInt(2))),
      Block(Vector(UnsignedByte.fromInt(3), UnsignedByte.fromInt(4)))
    ))

    val result = store.read(UnsignedByte.fromInt(0))

    assert(result == Address(List(UnsignedByte.fromInt(3), UnsignedByte.fromInt(1))))
  }

  test("Store can be read at the last index") {

    val store = Store(Vector(
      Block(Vector(UnsignedByte.fromInt(1), UnsignedByte.fromInt(2))),
      Block(Vector(UnsignedByte.fromInt(3), UnsignedByte.fromInt(4)))
    ))

    val result = store.read(UnsignedByte.fromInt(1))

    assert(result == Address(List(UnsignedByte.fromInt(4), UnsignedByte.fromInt(2))))
  }
