package net.mem_memov.binet.memory.inventory.walker

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.inventory.specific.Walker
import net.mem_memov.binet.memory.specific.inventory.specific.Walker.given

class TravelSuite extends munit.FunSuite:

  class AddressStub
  object OriginAddressStub extends AddressStub
  object IncrementedOriginAddressStub extends AddressStub
  object NextInventoryAddressStub extends AddressStub
  object ContentAddressStub extends AddressStub

  class InventoryStub
  given InventoryStub = new InventoryStub

  test("Walker stops at the inventory border") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (OriginAddressStub, NextInventoryAddressStub) => 0
          case _ => fail("unexpected")

    given general.inventory.GetNext[InventoryStub, AddressStub] with
      override def f(inventory: InventoryStub): AddressStub =
        NextInventoryAddressStub

    given general.inventory.Read[InventoryStub, AddressStub] with
      override def f(inventory: InventoryStub, origin: AddressStub): Either[String, AddressStub] =
        fail("unexpected")

    given general.address.Increment[AddressStub] with
      override def f(address: AddressStub): AddressStub =
        fail("unexpected")

    val walker = new Walker

    val result = walker.travel(
      0,
      OriginAddressStub,
      (result, item) => result + 1
    )

    assert(result.contains(0))
  }

  test("Walker propagates reading error") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (OriginAddressStub, NextInventoryAddressStub) => -1
          case _ => fail("unexpected")

    given general.inventory.GetNext[InventoryStub, AddressStub] with
      override def f(inventory: InventoryStub): AddressStub =
        NextInventoryAddressStub

    given general.inventory.Read[InventoryStub, AddressStub] with
      override def f(inventory: InventoryStub, origin: AddressStub): Either[String, AddressStub] =
        assert(origin.equals(OriginAddressStub))
        Left("Reading error")

    given general.address.Increment[AddressStub] with
      override def f(address: AddressStub): AddressStub =
        fail("unexpected")

    val walker = new Walker

    val result = walker.travel(
      0,
      OriginAddressStub,
      (result, item) => result + 1
    )

    assert(result == Left("Reading error"))
  }

  test("Walker feeds next item into the processing function") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (OriginAddressStub, NextInventoryAddressStub) => -1
          case (IncrementedOriginAddressStub, NextInventoryAddressStub) => 0
          case _ => fail("unexpected")

    given general.inventory.GetNext[InventoryStub, AddressStub] with
      override def f(inventory: InventoryStub): AddressStub =
        NextInventoryAddressStub

    given general.inventory.Read[InventoryStub, AddressStub] with
      override def f(inventory: InventoryStub, origin: AddressStub): Either[String, AddressStub] =
        assert(origin.equals(OriginAddressStub))
        Right(ContentAddressStub)

    given general.address.Increment[AddressStub] with
      override def f(address: AddressStub): AddressStub =
        assert(address.equals(OriginAddressStub))
        IncrementedOriginAddressStub

    val walker = new Walker

    val result = walker.travel(
      0,
      OriginAddressStub,
      (result, item) => result + 1
    )

    assert(result == Right(1))
  }
