package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given

class ToContentSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)

  test("Address gets converted to content") {

    val addressParts = List(b1, b1, b0)

    val address = Address(addressParts)

    val result = address.toContent

    assert(result.indices == Vector(b0, b1, b1))
  }
