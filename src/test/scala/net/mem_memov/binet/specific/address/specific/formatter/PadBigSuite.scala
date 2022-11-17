package net.mem_memov.binet.specific.address.specific.formatter

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.formatter.TrimBig
import net.mem_memov.binet.memory.specific.address.specific.*
import net.mem_memov.binet.memory.specific.address.specific.Formatter.given

class PadBigSuite extends munit.FunSuite:

  class Stub
  given trimmingFormatter: Stub = new Stub
  val formatter = new Formatter

  test("Formatter pads indices") {

    val originalIndices = List(UnsignedByte.maximum)
    val expectedIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    for {
      result <- formatter.padBig(3, originalIndices)
    } yield assert(result == expectedIndices)
  }

  test("Formatter fails padding indices if already too many") {

    val originalIndices = List(UnsignedByte.maximum, UnsignedByte.maximum, UnsignedByte.maximum)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val result = formatter.padBig(2, originalIndices)
    assert(result == Left("Address not padded: already too long"))
  }

  test("Formatter succeeds padding if indices can be trimmed") {

    val originalIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)
    val expectedIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    for {
      result <- formatter.padBig(3, originalIndices)
    } yield assert(result == expectedIndices)
  }

  test("Formatter skips padding if it is not needed") {

    val originalIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)

    given TrimBig[Stub] with
      override def f(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    for {
      result <- formatter.padBig(3, originalIndices)
    } yield assert(result.equals(originalIndices))
  }
