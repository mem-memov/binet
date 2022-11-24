package net.mem_memov.binet.memory.address.padder

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.general.trimmer.TrimBig
import net.mem_memov.binet.memory.specific.address.specific.*
import net.mem_memov.binet.memory.specific.address.specific.Padder.given

class PadBigSuite extends munit.FunSuite:

  val min = UnsignedByte.minimum
  val max = UnsignedByte.maximum
  
  class TrimmerStub
  given trimmer: TrimmerStub = new TrimmerStub

  test("Formatter pads indices") {

    val originalIndices = List(max)
    val expectedIndices = List(min, min, max)

    given TrimBig[TrimmerStub] with
      override def f(formatter: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val padder = new Padder

    for {
      result <- padder.padBig(3, originalIndices)
    } yield assert(result == expectedIndices)
  }

  test("Formatter fails padding indices if already too many") {

    val originalIndices = List(max, max, max)

    given TrimBig[TrimmerStub] with
      override def f(formatter: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val padder = new Padder

    val result = padder.padBig(2, originalIndices)
    assert(result == Left("Address not padded: already too long"))
  }

  test("Formatter succeeds padding if indices can be trimmed") {

    val originalIndices = List(min, min, min, max)
    val expectedIndices = List(min, min, max)

    given TrimBig[TrimmerStub] with
      override def f(formatter: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val padder = new Padder

    for {
      result <- padder.padBig(3, originalIndices)
    } yield assert(result == expectedIndices)
  }

  test("Formatter skips padding if it is not needed") {

    val originalIndices = List(min, min, max)

    given TrimBig[TrimmerStub] with
      override def f(formatter: TrimmerStub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    val padder = new Padder

    for {
      result <- padder.padBig(3, originalIndices)
    } yield assert(result.equals(originalIndices))
  }
