package net.mem_memov.binet.specific.specificAddress

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given

class EmptyCheckingSuite extends munit.FunSuite:

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
