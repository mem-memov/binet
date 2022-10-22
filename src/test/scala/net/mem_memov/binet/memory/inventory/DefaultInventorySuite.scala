package net.mem_memov.binet.memory.inventory

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.factory.DefaultFactory

class DefaultInventorySuite extends munit.FunSuite:

  test("Append and read the first address") {

    val factory = DefaultFactory()

    val inventory = factory.makeEmptyInventory()

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

//  test("Append and read addresses") {
//
//    val inventory = Inventory()
//
//    val start = Inventory.start
//
//    val (_, addresses) = (0 to 5).foldLeft((start, List(start))) {
//      case ((previous, addresses), _) =>
//        val next = previous.increment
//        (next, next :: addresses)
//    }
//
//    val (inventoryWitDestinations, destinations) = addresses.foldLeft((inventory, List.empty[Address])) {
//      case ((inventory, destinations), content) =>
//        for {
//          destination <- Right(inventory.next)
//          updatedInventory <- inventory.append(content)
//        } yield (updatedInventory, destination :: destinations)
//    }
//
//    destinations.zip(addresses).foreach {
//      case (origin, expected) =>
//        for {
//          content <- inventoryWitDestinations.read(origin)
//        } yield assert(content == expected)
//    }
//  }
