package net.mem_memov.binet.memory.live

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.live.defaultFactory._

class DefaultInventoryIntergationSuite extends munit.FunSuite:

  test("Inventory appends and reads the first address") {

    val inventory = DefaultFactory().emptyInventory

    val start = DefaultFactory.emptyAddress

    val result = for {
      inventory <- inventory.append(start)
      destination <- inventory.read(start)
    } yield destination

    result match {
      case Right(destination) => assert(destination.isEqual(start))
      case Left(error) => fail(error)
    }
  }

