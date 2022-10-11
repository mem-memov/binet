package net.mem_memov.binet.memory

class UnsignedByteSuite extends munit.FunSuite:

  test("UnsignedByte converts to Int") {
    (0 to 255).map { i =>
      val unsignedByte = UnsignedByte.fromInt(i)
      val result = unsignedByte.toInt
      assert(result == i)
    }
  }
