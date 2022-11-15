package net.mem_memov.binet.specific.specificAddress.specific.specificResizer

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.specific.Resizer
import net.mem_memov.binet.memory.specific.address.specific.Resizer.given


class IncrementingSuite extends munit.FunSuite:

  test("Resizer increments indices") {

    val originalIndices = List(UnsignedByte.fromInt(231), UnsignedByte.fromInt(245))
    val expectedIndices = List(UnsignedByte.fromInt(231), UnsignedByte.fromInt(246))

    val resizer = new Resizer

    val result = resizer.increment(originalIndices)

    assert(result == expectedIndices)
  }

  test("Resizer increments the next index") {

    val originalIndices = List(UnsignedByte.fromInt(231), UnsignedByte.fromInt(255))
    val expectedIndices = List(UnsignedByte.fromInt(232), UnsignedByte.fromInt(0))

    val resizer = new Resizer

    val result = resizer.increment(originalIndices)

    assert(result == expectedIndices)
  }

  test("Resizer increments adding new index") {

    val originalIndices = List(UnsignedByte.fromInt(255), UnsignedByte.fromInt(255))
    val expectedIndices = List(UnsignedByte.fromInt(1), UnsignedByte.fromInt(0), UnsignedByte.fromInt(0))

    val resizer = new Resizer

    val result = resizer.increment(originalIndices)

    assert(result == expectedIndices)
  }
