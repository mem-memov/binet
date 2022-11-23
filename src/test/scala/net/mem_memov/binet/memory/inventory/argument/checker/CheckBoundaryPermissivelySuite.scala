package net.mem_memov.binet.memory.inventory.argument.checker

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Checker
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Checker.given

class CheckBoundaryPermissivelySuite extends munit.FunSuite:

  class AddressStub
  object InventoryNextAddressStub extends AddressStub
  object CheckedAddressStub extends AddressStub
  object ZeroAddressStub extends AddressStub

  class FactoryStub
  given FactoryStub = new FactoryStub

  test("Checker approves an address below the inventory boundary") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (InventoryNextAddressStub, CheckedAddressStub) => 1
          case _ => fail("unexpected")

    given general.factory.ZeroAddress[FactoryStub, AddressStub] with
      override def f(): AddressStub =
        fail("unexpected")

    val checker = new Checker

    val result = checker.checkBoundaryPermissively(InventoryNextAddressStub, CheckedAddressStub)

    assert(result.isRight)
  }

  test("Checker approves the first address at the inventory boundary") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (InventoryNextAddressStub, CheckedAddressStub) => 1
          case (InventoryNextAddressStub, ZeroAddressStub) => 0
          case _ => fail("unexpected")

    given general.factory.ZeroAddress[FactoryStub, AddressStub] with
      override def f(): AddressStub =
        ZeroAddressStub

    val checker = new Checker

    val result = checker.checkBoundaryPermissively(InventoryNextAddressStub, CheckedAddressStub)

    assert(result.isRight)
  }

  test("Checker disapproves an address above the inventory boundary") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (InventoryNextAddressStub, CheckedAddressStub) => -1
          case (InventoryNextAddressStub, ZeroAddressStub) => 1
          case _ => fail("unexpected")

    given general.factory.ZeroAddress[FactoryStub, AddressStub] with
      override def f(): AddressStub =
        ZeroAddressStub

    val checker = new Checker

    val result = checker.checkBoundaryPermissively(InventoryNextAddressStub, CheckedAddressStub)

    assert(result == Left("Address out of permissive boundary"))
  }
