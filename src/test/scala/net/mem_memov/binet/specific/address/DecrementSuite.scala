package net.mem_memov.binet.specific.address

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given
import net.mem_memov.binet.memory.specific.address.general.resizer.Decrement

class DecrementSuite extends munit.FunSuite:

  class Stub
  given decrementingResizer: Stub = new Stub

  test("Address gets decremented") {

    val decrementedIndices = List(UnsignedByte.fromInt(1))
    val originalIndices = List(UnsignedByte.fromInt(2))

    val address = Address(originalIndices)

    given Decrement[Stub] with
      override def f(resizer: Stub, indices: List[UnsignedByte]): Either[String, List[UnsignedByte]] =
        assert(indices.equals(originalIndices))
        Right(decrementedIndices)

    for {
      result <- address.decrement
    } assert(result.parts.equals(decrementedIndices))
  }