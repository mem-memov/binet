package net.mem_memov.binet.memory.address.specific.resizer

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.specific.Resizer
import net.mem_memov.binet.memory.specific.address.specific.Resizer.given

class DecrementSuite extends munit.FunSuite:

  test("Resizer decrements indices") {

    val originalIndices = List(UnsignedByte.fromInt(231), UnsignedByte.fromInt(245))
    val expectedIndices = List(UnsignedByte.fromInt(231), UnsignedByte.fromInt(244))

    val resizer = new Resizer

    val result = resizer.decrement(originalIndices)

    assert(result == Right(expectedIndices))
  }

  test("Resizer fails decrementing empty indices") {

    val originalIndices = List.empty[UnsignedByte]

    val resizer = new Resizer

    val result = resizer.decrement(originalIndices)

    assert(result == Left("Address not decremented: no index available"))
  }

  test("Resizer fails decrementing zero") {

    val originalIndices = List(UnsignedByte.minimum)

    val resizer = new Resizer

    val result = resizer.decrement(originalIndices)

    assert(result == Left("Address not decremented: already at minimum"))
  }

  test("Resizer decrements the next index") {

    val originalIndices = List(UnsignedByte.fromInt(231), UnsignedByte.fromInt(0))
    val expectedIndices = List(UnsignedByte.fromInt(230), UnsignedByte.fromInt(255))

    val resizer = new Resizer

    val result = resizer.decrement(originalIndices)

    assert(result == Right(expectedIndices))
  }

  test("Resizer decrements removing an index") {

    val originalIndices = List(UnsignedByte.fromInt(1), UnsignedByte.fromInt(0))
    val expectedIndices = List(UnsignedByte.fromInt(255))

    val resizer = new Resizer

    val result = resizer.decrement(originalIndices)

    assert(result == Right(expectedIndices))
  }