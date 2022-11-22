package net.mem_memov.binet.memory.inventory.walker

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.inventory.specific.Walker
import net.mem_memov.binet.memory.specific.inventory.specific.Walker.given
import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

class TravelSuite extends munit.FunSuite:

  class AddressStub
  val originAddressStub = new AddressStub
  val nextInventoryAddressStub = new AddressStub

  class InventoryStub
  given InventoryStub = new InventoryStub

  test("Walker stops at the inventory border") {

    given Ordering[AddressStub] with
      override def compare(x: AddressStub, y: AddressStub): Int =
        (x, y) match
          case (originAddressStub, nextInventoryAddressStub) => 0
          case _ => fail("unexpected")

    given CanEqual[AddressStub, AddressStub] = CanEqual.derived

    given general.inventory.Next[InventoryStub, AddressStub] with
      override def f(inventory: InventoryStub): AddressStub =
        nextInventoryAddressStub

    given general.inventory.Read[InventoryStub, AddressStub] with
      override def f(inventory: InventoryStub, origin: AddressStub): Either[String, AddressStub] =
        fail("unexpected")

    given general.address.Increment[AddressStub] with
      override def f(address: AddressStub): AddressStub =
        fail("unexpected")

    val c = originAddressStub == nextInventoryAddressStub

    val walker = new Walker

    val result = walker.travel(
      0,
      originAddressStub,
      (result, item) => result + 1
    )
  }
