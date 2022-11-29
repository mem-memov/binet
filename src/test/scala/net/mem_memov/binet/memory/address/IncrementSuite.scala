package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given
import net.mem_memov.binet.memory.specific.address.general.resizer.Increment

class IncrementSuite extends munit.FunSuite:

  class ResizerStub
  given ResizerStub = new ResizerStub

  test("Address gets incremented") {

    val originalIndices = List(UnsignedByte.fromInt(2))
    val incrementedIndices = List(UnsignedByte.fromInt(3))

    given Increment[ResizerStub] with
      override def f(indices: List[UnsignedByte]) =
        assert(indices.equals(originalIndices))
        incrementedIndices

    val address = Address(originalIndices)

    val result = address.increment()

    assert(result.parts.equals(incrementedIndices))
  }
