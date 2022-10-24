package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.DefaultFactory

class DefaultInventorySuite extends munit.FunSuite:

  test("Append and read the first address") {

    val inventory = DefaultFactory().makeEmptyInventory()

    val start = DefaultInventory.start

    val result = for {
      inventory <- inventory.append(start)
      destination <- inventory.read(start)
    } yield destination

    result match {
      case Right(destination) => assert(destination == start)
      case Left(error) => fail(error)
    }
  }

  test("Append and read the first address") {

    val inventory = DefaultFactory().makeEmptyInventory()

    val start = inventory.next

    for {
      updatedInventory <- inventory.append(start)
      result <- updatedInventory.read(start)
    } yield updatedInventory match {
      case DefaultInventory(next, root) => assert(result == start)
      case _ => fail("unexpected")
    }
  }
