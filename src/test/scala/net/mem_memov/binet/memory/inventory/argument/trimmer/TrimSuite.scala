package net.mem_memov.binet.memory.inventory.argument.trimmer

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Trimmer
import net.mem_memov.binet.memory.specific.inventory.specific.argument.specific.Trimmer.given

class TrimSuite extends munit.FunSuite:

  class AddressStub
  val originalAddressStub = new AddressStub
  val trimmedAddressStub = new AddressStub
  val zeroAddressStub: AddressStub = new AddressStub

  class FactoryStub
  given factoryStub: FactoryStub = new FactoryStub

  test("Trimmer discards big significant bytes") {

    given general.address.IsEmpty[AddressStub] with
      override def f(address: AddressStub): Boolean =
        assert(address.equals(originalAddressStub))
        false

    given general.address.TrimBig[AddressStub] with
      override def f(address: AddressStub): AddressStub =
        assert(address.equals(originalAddressStub))
        trimmedAddressStub

    given general.factory.ZeroAddress[FactoryStub, AddressStub] with
      override def f(): AddressStub =
        fail("unexpected")

    val trimmer = new Trimmer

    val result = trimmer.trim(originalAddressStub)

    assert(result.equals(trimmedAddressStub))
  }

  test("Trimmer provides an empty address") {

    given general.address.IsEmpty[AddressStub] with
      override def f(address: AddressStub): Boolean =
        assert(address.equals(originalAddressStub))
        true

    given general.address.TrimBig[AddressStub] with
      override def f(address: AddressStub): AddressStub =
        fail("unexpected")

    given general.factory.ZeroAddress[FactoryStub, AddressStub] with
      override def f(): AddressStub =
        zeroAddressStub

    val trimmer = new Trimmer

    val result = trimmer.trim(originalAddressStub)

    assert(result.equals(zeroAddressStub))
  }
