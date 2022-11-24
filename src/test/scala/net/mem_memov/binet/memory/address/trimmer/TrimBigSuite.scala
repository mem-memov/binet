package net.mem_memov.binet.memory.address.trimmer

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.address.specific.Trimmer

class TrimBigSuite extends munit.FunSuite:

  val min = UnsignedByte.minimum
  val max = UnsignedByte.maximum

  test("Formatter trims indices") {

    val originalIndices = List(min, min, max)
    val expectedIndices = List(max)

    val trimmer = new Trimmer

    val result = trimmer.trimBig(originalIndices)

    assert(result == expectedIndices)
  }

  test("Formatter skips trimming indices if it is not needed") {

    val originalIndices = List(max, max)

    val trimmer = new Trimmer

    val result = trimmer.trimBig(originalIndices)

    assert(result.equals(originalIndices))
  }