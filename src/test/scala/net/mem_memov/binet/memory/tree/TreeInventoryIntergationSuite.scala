package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.Inventory._
import net.mem_memov.binet.memory.tree.treeFactory._

class TreeInventoryIntergationSuite extends munit.FunSuite:

  test("Inventory appends and reads the first address") {

    val inventory = TreeFactory().emptyInventory

    val start = TreeFactory.emptyAddress

    val result = for {
      inventory <- inventory.append(start)
      destination <- inventory.read(start)
    } yield destination

    result match {
      case Right(destination) => assert(destination.isEqual(start))
      case Left(error) => fail(error)
    }
  }

