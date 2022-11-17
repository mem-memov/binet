package net.mem_memov.binet.specific.content

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Address, Content}
import net.mem_memov.binet.memory.specific.Content.given

class ToAddressSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)
  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)
  val b4 = UnsignedByte.fromInt(4)
  val b5 = UnsignedByte.fromInt(5)

  test("Content converts to address") {

    val content = Content(Vector(b1, b2, b3, b4, b5))

    val result = content.toAddress

    assert(result == Address(List(b5, b4, b3, b2, b1)))
  }
