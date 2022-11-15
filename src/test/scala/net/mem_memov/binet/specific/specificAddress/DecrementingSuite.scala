package net.mem_memov.binet.specific.specificAddress

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given
import net.mem_memov.binet.memory.specific.specificAddress.general.resizer.DecrementingResizer

class DecrementingSuite extends munit.FunSuite:

  class Stub
  given decrementingResizer: Stub = new Stub

  test("Address gets decremented") {

    val decrementedIndices = List(UnsignedByte.fromInt(1))
    val originalIndices = List(UnsignedByte.fromInt(2))

    val address = Address(originalIndices)

    given DecrementingResizer[Stub] with
      override def decrementIndices(resizer: Stub, indices: List[UnsignedByte]): Either[String, List[UnsignedByte]] =
        assert(indices.equals(originalIndices))
        Right(decrementedIndices)

    for {
      result <- address.decrement
    } assert(result.parts.equals(decrementedIndices))
  }