package net.mem_memov.binet.memory.path

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Path
import net.mem_memov.binet.memory.specific.Path.given

class IsEmptySuite extends munit.FunSuite:

  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)

  test("Path confirms being empty") {

    val path = Path(Vector())

    val result = path.isEmpty

    assert(result)
  }

  test("Path denies being empty") {

    val path = Path(Vector(b2, b3))

    val result = path.isEmpty

    assert(!result)
  }
