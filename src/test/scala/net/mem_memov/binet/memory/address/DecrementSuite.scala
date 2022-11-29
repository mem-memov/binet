package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given
import net.mem_memov.binet.memory.specific.address.general.resizer.Decrement

class DecrementSuite extends munit.FunSuite:

  class ResizerStub
  given ResizerStub = new ResizerStub

  test("Address gets decremented") {

    val originalIndices = List(UnsignedByte.fromInt(2))
    val decrementedIndices = List(UnsignedByte.fromInt(1))

    val address = Address(originalIndices)

    given Decrement[ResizerStub] with
      override def f(indices: List[UnsignedByte]): Either[String, List[UnsignedByte]] =
        assert(indices.equals(originalIndices))
        Right(decrementedIndices)

    for {
      result <- address.decrement
    } assert(result.parts.equals(decrementedIndices))
  }