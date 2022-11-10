package net.mem_memov.binet.specific.specificAddress.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.specificAddress.general.formatter.TrimmingFormatter
import net.mem_memov.binet.memory.specific.specificAddress.specific.SpecificFormatter.given
import net.mem_memov.binet.memory.specific.specificAddress.specific.*

class SpecificFormatterSuite extends munit.FunSuite:

  class Stub
  given trimmingFormatter: Stub = new Stub
  val formatter = new SpecificFormatter

  test("Formatter pads indices") {

    val originalIndices = List(UnsignedByte.maximum)
    val expectedIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)

    given TrimmingFormatter[Stub] with
      override def trimBigIndices(formatter: Stub, indices: List[UnsignedByte]): List[UnsignedByte] =
        assert(indices == originalIndices)
        indices

    for {
      result <- formatter.padBig(3, originalIndices)
    } yield assert(result == expectedIndices)
  }
