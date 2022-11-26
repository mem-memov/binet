package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Inventory
import scala.math.Ordering.Implicits.infixOrderingOps // enables address comparison operators

class MemorySuite extends munit.FunSuite:

  test("Memory") {

    import Preamble._
    import Preamble.given

    val block = factory.emptyBlock()

    block.write(UnsignedByte.fromInt(2), UnsignedByte.fromInt(3))

    val inventory: Inventory = factory.emptyInventory()

    val start = factory.zeroAddress()

    inventory.append(start)


    for {
      modifiedInventory <- inventory.append(start)
    } yield assert(modifiedInventory.next equiv start.increment())

    for {
      content <- inventory.read(start)
    } assert(content equiv start)
  }
