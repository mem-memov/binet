package net.mem_memov.binet.specific.specificAddress

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.SpecificAddress
import net.mem_memov.binet.memory.specific.SpecificAddress.given

class EmptyCheckingSuite extends munit.FunSuite:

  test("Checker confirms that an address is empty") {

    val address = SpecificAddress(List.empty[UnsignedByte])

    val result = address.isEmpty

    assert(result)
  }

  test("Checker denies that an address is empty") {

    val address = SpecificAddress(List(UnsignedByte.maximum))

    val result = address.isEmpty

    assert(!result)
  }
