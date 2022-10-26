package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.factory.DefaultFactory
import net.mem_memov.binet.memory.factory.defaultFactory.*

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

