package net.mem_memov.binet.specific.address.specific.formatter

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.formatter.TrimBig
import net.mem_memov.binet.memory.specific.address.specific.*
import net.mem_memov.binet.memory.specific.address.specific.Formatter.given

class PadBigSuite extends munit.FunSuite:

  val min = UnsignedByte.minimum
  val max = UnsignedByte.maximum
  
  class FormatterStub
  given trimmingFormatter: FormatterStub = new FormatterStub

  test("Formatter pads indices") {

    val originalIndices = List(max)
    val expectedIndices = List(min, min, max)

    given TrimBig[FormatterStub] with
      override def f(formatter: FormatterStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val formatter = new Formatter

    for {
      result <- formatter.padBig(3, originalIndices)
    } yield assert(result == expectedIndices)
  }

  test("Formatter fails padding indices if already too many") {

    val originalIndices = List(max, max, max)

    given TrimBig[FormatterStub] with
      override def f(formatter: FormatterStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val formatter = new Formatter

    val result = formatter.padBig(2, originalIndices)
    assert(result == Left("Address not padded: already too long"))
  }

  test("Formatter succeeds padding if indices can be trimmed") {

    val originalIndices = List(min, min, min, max)
    val expectedIndices = List(min, min, max)

    given TrimBig[FormatterStub] with
      override def f(formatter: FormatterStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val formatter = new Formatter

    for {
      result <- formatter.padBig(3, originalIndices)
    } yield assert(result == expectedIndices)
  }

  test("Formatter skips padding if it is not needed") {

    val originalIndices = List(min, min, max)

    given TrimBig[FormatterStub] with
      override def f(formatter: FormatterStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val formatter = new Formatter

    for {
      result <- formatter.padBig(3, originalIndices)
    } yield assert(result.equals(originalIndices))
  }
