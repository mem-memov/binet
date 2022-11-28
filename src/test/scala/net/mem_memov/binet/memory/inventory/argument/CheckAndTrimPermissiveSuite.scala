package net.mem_memov.binet.memory.inventory.argument

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.inventory.specific.Argument
import net.mem_memov.binet.memory.specific.inventory.specific.Argument.given

class CheckAndTrimPermissiveSuite extends munit.FunSuite:

  class AddressStub
  val inventoryNextAddressStub: AddressStub = new AddressStub
  val argumentAddressStub: AddressStub = new AddressStub
  val trimmedAddressStub: AddressStub = new AddressStub

  class CheckerStub
  given CheckerStub = new CheckerStub

  class TrimmerStub
  given TrimmerStub = new TrimmerStub

  test("Argument approves an address permissively and trims it") {

    given specific.inventory.specific.argument.general.trimmer.Trim[TrimmerStub, AddressStub] with
      override def f(address: AddressStub): AddressStub =
        assert(address.equals(argumentAddressStub))
        trimmedAddressStub

    given specific.inventory.specific.argument.general.checker.CheckBoundaryPermissively[CheckerStub, AddressStub] with
      override def f(next: AddressStub, address: AddressStub): Either[String, Unit] =
        assert(next.equals(inventoryNextAddressStub))
        assert(address.equals(trimmedAddressStub))
        Right(())

    val argument = new Argument

    val result = argument.checkAndTrimPermissive(inventoryNextAddressStub, argumentAddressStub)

    assert(result.contains(trimmedAddressStub))
  }

  test("Argument rejects an address permissively") {

    given specific.inventory.specific.argument.general.trimmer.Trim[TrimmerStub, AddressStub] with
      override def f(address: AddressStub): AddressStub =
        assert(address.equals(argumentAddressStub))
        trimmedAddressStub

    given specific.inventory.specific.argument.general.checker.CheckBoundaryPermissively[CheckerStub, AddressStub] with
      override def f(next: AddressStub, address: AddressStub): Either[String, Unit] =
        assert(next.equals(inventoryNextAddressStub))
        assert(address.equals(trimmedAddressStub))
        Left("error message")

    val argument = new Argument

    val result = argument.checkAndTrimPermissive(inventoryNextAddressStub, argumentAddressStub)

    assert(result == Left("error message"))
  }
