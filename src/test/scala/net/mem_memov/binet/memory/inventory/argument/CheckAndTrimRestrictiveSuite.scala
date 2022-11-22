package net.mem_memov.binet.memory.inventory.argument

import net.mem_memov.binet.memory.specific
import net.mem_memov.binet.memory.specific.inventory.specific.Argument

class CheckAndTrimRestrictiveSuite extends munit.FunSuite:

  class AddressStub
  val inventoryNextAddressStub: AddressStub = new AddressStub
  val argumentAddressStub: AddressStub = new AddressStub
  val trimmedAddressStub: AddressStub = new AddressStub

  class CheckerStub
  given CheckerStub = new CheckerStub

  class TrimmerStub
  given TrimmerStub = new TrimmerStub

  test("Argument approves an address restrictively and trims it") {

    given specific.inventory.specific.argument.general.trimmer.Trim[TrimmerStub, AddressStub] with
      override def f(trimmer: TrimmerStub, address: AddressStub): AddressStub =
        assert(address.equals(argumentAddressStub))
        trimmedAddressStub

    given specific.inventory.specific.argument.general.checker.CheckBoundaryRestrictively[CheckerStub, AddressStub] with
      override def f(checker: CheckerStub, next: AddressStub, address: AddressStub): Either[String, Unit] =
        assert(next.equals(inventoryNextAddressStub))
        assert(address.equals(trimmedAddressStub))
        Right(())

    val argument = new Argument

    val result = argument.checkAndTrimRestrictive(inventoryNextAddressStub, argumentAddressStub)

    assert(result.contains(trimmedAddressStub))
  }

  test("Argument rejects an address restrictively") {

    given specific.inventory.specific.argument.general.trimmer.Trim[TrimmerStub, AddressStub] with
      override def f(trimmer: TrimmerStub, address: AddressStub): AddressStub =
        assert(address.equals(argumentAddressStub))
        trimmedAddressStub

    given specific.inventory.specific.argument.general.checker.CheckBoundaryRestrictively[CheckerStub, AddressStub] with
      override def f(checker: CheckerStub, next: AddressStub, address: AddressStub): Either[String, Unit] =
        assert(next.equals(inventoryNextAddressStub))
        assert(address.equals(trimmedAddressStub))
        Left("error message")

    val argument = new Argument

    val result = argument.checkAndTrimRestrictive(inventoryNextAddressStub, argumentAddressStub)

    assert(result == Left("error message"))
  }
