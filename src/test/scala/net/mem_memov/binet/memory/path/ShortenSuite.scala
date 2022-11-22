package net.mem_memov.binet.memory.path

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Path
import net.mem_memov.binet.memory.specific.Path.given

class ShortenSuite extends munit.FunSuite:

  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)

  test("Path gets shorter") {

    val path = Path(Vector(b2, b3))

    for {
      result <- path.shorten()
    } yield
      assert(result.rest == Path(Vector(b3)))
      assert(result.index == b2)
  }

  test("Path doesn't get shorter if empty") {

    val path = Path(Vector())

    val result = path.shorten()

    assert(result == Left("Path couldn't be used"))
  }
