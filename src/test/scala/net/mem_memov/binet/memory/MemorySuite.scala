package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Inventory

class MemorySuite extends munit.FunSuite:

  test("Memory") {

    import Preamble._
    import Preamble.given

    val block = factory.emptyBlock()

    block.write(UnsignedByte.fromInt(2), UnsignedByte.fromInt(3))

    val inventory: Inventory = factory.emptyInventory()

    val start = factory.zeroAddress()

    inventory.root.storeOption.map(_.read(UnsignedByte.fromInt(3)))
//    inventory.root.stockOption.map(_.read(UnsignedByte.fromInt(3), start.toPath))


//    for {
//      result <- inventory.append(start)
//    } yield assert(true)

  }
