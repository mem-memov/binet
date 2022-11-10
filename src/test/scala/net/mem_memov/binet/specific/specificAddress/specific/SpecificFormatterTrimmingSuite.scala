package net.mem_memov.binet.specific.specificAddress.specific

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.specificAddress.specific.SpecificFormatter

class SpecificFormatterTrimmingSuite extends munit.FunSuite:

  val formatter = new SpecificFormatter

  test("Formatter trims indices") {

    val originalIndices = List(UnsignedByte.minimum, UnsignedByte.minimum, UnsignedByte.maximum)
    val expectedIndices = List(UnsignedByte.maximum)

    val result = formatter.trimBig(originalIndices)

    assert(result == expectedIndices)
  }

  test("Formatter skips trimming indices if it is not needed") {

    val originalIndices = List(UnsignedByte.maximum, UnsignedByte.maximum)

    val result = formatter.trimBig(originalIndices)

    assert(result.equals(originalIndices))
  }