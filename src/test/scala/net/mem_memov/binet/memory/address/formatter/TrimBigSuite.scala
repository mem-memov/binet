package net.mem_memov.binet.memory.address.specific.formatter

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.specific.Formatter

class TrimBigSuite extends munit.FunSuite:

  val min = UnsignedByte.minimum
  val max = UnsignedByte.maximum

  test("Formatter trims indices") {

    val originalIndices = List(min, min, max)
    val expectedIndices = List(max)

    val formatter = new Formatter

    val result = formatter.trimBig(originalIndices)

    assert(result == expectedIndices)
  }

  test("Formatter skips trimming indices if it is not needed") {

    val originalIndices = List(max, max)

    val formatter = new Formatter

    val result = formatter.trimBig(originalIndices)

    assert(result.equals(originalIndices))
  }