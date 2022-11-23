package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.{general, specific}
import net.mem_memov.binet.memory.specific.{Address, Element, Inventory}
import net.mem_memov.binet.memory.specific.Inventory.given

class FoldSuite extends munit.FunSuite:

  class FactoryStub
  given FactoryStub = new FactoryStub

  class WalkerStub
  given WalkerStub = new WalkerStub

  test("Inventory folds to a value") {

    val inventoryNextAddress = Address(List.empty)
    val inventoryRootElement = Element(None, None)

    val startAddress = Address(List.empty)
    val contentAddress = Address(List.empty)

    val processingFunction = (accumulator: Int, item: general.Item[Address]) => accumulator + 1

    given general.factory.ZeroAddress[FactoryStub, Address] with
      override def f(): Address =
        startAddress

    given specific.inventory.general.walker.Travel[WalkerStub, Address] with
      override def f[RESULT](walker: WalkerStub, result: RESULT, origin: Address, process: (RESULT, general.Item[Address]) => RESULT): Either[String, RESULT] =
        assert(result == 0)
        assert(origin.equals(startAddress))
        assert(process.equals(processingFunction))
        Right(process(result, general.Item(origin, contentAddress)))

    val inventory = new Inventory(inventoryNextAddress, inventoryRootElement)

    for {
      result <- inventory.fold[Int](0)(processingFunction)
    } yield assert(result == 1)
  }
