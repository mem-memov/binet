package net.mem_memov.binet.specific.specificAddress

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.SpecificAddress
import net.mem_memov.binet.memory.specific.SpecificAddress.given

class ComparabilityCheckingSuite extends munit.FunSuite:

  test("Checker returns always true") {

    val anotherAddress = SpecificAddress(List.empty[UnsignedByte])

    val address = SpecificAddress(List.empty[UnsignedByte])

    val result = address.canCompare(anotherAddress)

    assert(result)
  }
