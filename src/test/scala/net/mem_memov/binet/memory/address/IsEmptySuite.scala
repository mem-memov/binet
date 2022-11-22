package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given

class IsEmptySuite extends munit.FunSuite:

  test("Checker confirms that an address is empty") {

    val address = Address(List.empty[UnsignedByte])

    val result = address.isEmpty

    assert(result)
  }

  test("Checker denies that an address is empty") {

    val address = Address(List(UnsignedByte.maximum))

    val result = address.isEmpty

    assert(!result)
  }
