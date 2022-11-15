package net.mem_memov.binet.specific.specificAddress.specific.specificFormatter

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.specific.Formatter

class TrimmingSuite extends munit.FunSuite:

  val formatter = new Formatter

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