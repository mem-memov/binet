package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given

class ToLittleEndianSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)
  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)

  test("Address gets converted to an array of bytes in little-endian order") {

    val address = Address(List(b3, b2, b1, b0))

    val result = address.toLittleEndian

    assert(result sameElements Array[Byte](-125, -126, -127, -128))
  }
