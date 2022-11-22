package net.mem_memov.binet.memory.inventory.argument.checker

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Checker
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Checker.given

class CheckBoundaryRestrictivelySuite extends munit.FunSuite:

  class AddressStub
  val inventoryNextAddressStub: AddressStub = new AddressStub
  val checkedAddressStub: AddressStub = new AddressStub

  test("Checker approves an address below the inventory boundary") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (inventoryNextAddressStub, checkedAddressStub) => 1
          case _ => fail("unexpected")

    val checker = new Checker

    val result = checker.checkBoundaryRestrictively(inventoryNextAddressStub, checkedAddressStub)

    assert(result.isRight)
  }

  test("Checker disapproves an address above the inventory boundary") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (inventoryNextAddressStub, checkedAddressStub) => -1
          case _ => fail("unexpected")

    val checker = new Checker

    val result = checker.checkBoundaryRestrictively(inventoryNextAddressStub, checkedAddressStub)

    assert(result == Left("Address out of restrictive boundary"))
  }

  test("Checker disapproves an address at the inventory boundary") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (inventoryNextAddressStub, checkedAddressStub) => 0
          case _ => fail("unexpected")

    val checker = new Checker

    val result = checker.checkBoundaryRestrictively(inventoryNextAddressStub, checkedAddressStub)

    assert(result == Left("Address out of restrictive boundary"))
  }
