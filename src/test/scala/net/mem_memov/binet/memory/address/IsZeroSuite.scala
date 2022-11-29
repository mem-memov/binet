package net.mem_memov.binet.memory.address

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Address
import net.mem_memov.binet.memory.specific.Address.given

class IsZeroSuite extends munit.FunSuite:

  val b0 = UnsignedByte.minimum
  val b1 = UnsignedByte.fromInt(1)

  test("Address confirms being zero") {

    val address = Address(List(b0))

    val result = address.isZero

    assert(result)
  }

  test("Address denies being zero") {

    val address = Address(List(b1, b0))

    val result = address.isZero

    assert(!result)
  }

  test("Address requires length one to be zero") {

    val address = Address(List(b0, b0))

    val result = address.isZero

    assert(!result)
  }

  test("Address denies empty address is zero address") {

    val address = Address(List())

    val result = address.isZero

    assert(!result)
  }
