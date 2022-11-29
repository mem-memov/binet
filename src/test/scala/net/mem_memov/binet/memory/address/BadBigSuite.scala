package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given
import net.mem_memov.binet.memory.specific.address.general.padder.PadBig

class BadBigSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)

  class PadderStub
  given PadderStub = new PadderStub

  test("Address gets padded") {

    val originalParts = List(b1)
    val paddedParts = List(b0, b0, b0, b1)

    given a:PadBig[PadderStub] with
      override def f(target: Int, indices: List[UnsignedByte]) =
        assert(target == 4)
        assert(indices.equals(originalParts))
        Right(paddedParts)

    val address = Address(originalParts)

    for {
      result <- address.padBig(4)
    } assert(result.parts.equals(paddedParts))
  }

  test("Address doesn't get padded") {

    val originalParts = List(b1)
    val paddedParts = List(b0, b0, b0, b1)

    given PadBig[PadderStub] with
      override def f(target: Int, indices: List[UnsignedByte]) =
        assert(target == 2)
        Left("error message")

    val address = Address(originalParts)

    val result = address.padBig(2)
    assert(result == Left("error message"))
  }
