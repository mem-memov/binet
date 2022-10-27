package net.mem_memov.binet.memory.tree.treeInventory.argument

import net.mem_memov.binet.memory.tree.treeFactory.{AddressFactory, UnusedAddressFactory}
import net.mem_memov.binet.memory.tree.treeInventory.argument.ArgumentService
import net.mem_memov.binet.memory.{Address, UnusedAddress}

class ArgumentServiceUnitSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  given AddressFactory = new UnusedAddressFactory(failMethod) {}

  test("It succeeds checking content and trimming it") {

    val trimmedContentAddress = new UnusedAddress(failMethod) {}

    val contentAddress = new UnusedAddress(failMethod):
      override def isEmpty: Boolean =
        false
      override def trimBig: Address =
        trimmedContentAddress

    val nextAddress = new UnusedAddress(failMethod):
      override def canCompare(that: Address): Boolean =
        assert(that.equals(contentAddress))
        true
      override def isLessOrEqual(that: Address): Boolean =
        assert(that.equals(trimmedContentAddress))
        false

    val argumentService = new ArgumentService()

    for {
      result <- argumentService.checkAndTrimPermissive(nextAddress, contentAddress)
    } yield assert(result.equals(trimmedContentAddress))
  }

  test("It fails because of content type incompatibility") {

    val contentAddress = new UnusedAddress(failMethod) {}

    val nextAddress = new UnusedAddress(failMethod) :
      override def canCompare(that: Address): Boolean =
        assert(that.equals(contentAddress))
        false

    val preparingContent = new ArgumentService()

    val result = preparingContent.checkAndTrimPermissive(nextAddress, contentAddress)

    result match
      case Left(message) => assert(message == "Wrong address type")
      case Right(_) => fail("unexpected")
  }
