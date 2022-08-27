package net.mem_memov.binet.memory

class InventorySuite extends munit.FunSuite:

  test("Append and read the first address") {

    val inventory = new Inventory

    val start = inventory.start

    inventory.append(start) match
      case Some(destination) =>
        assert(destination == start)
      case _ =>
        fail("failed appending")
  }

//  test("Append and read addresses") {
//
//    val memory = new Memory
//
//    val start = memory.start
//
//    val addresses: List[Address] = (0 to 5).foldLeft((start, List(start))) {
//      case ((previous, addresses), _) =>
//        val next = previous.increment
//        (next, next :: addresses)
//    }._2.reverse
//
//    val destinations = addresses.map { content =>
//      memory.append(content) match
//        case memory.Appended(destination) =>
//          destination
//        case problem =>
//          println(content)
//          println(problem)
//          fail()
//    }
//
//    destinations.zip(addresses).foreach {
//      case (origin, expected) =>
//        memory.read(origin) match
//          case memory.ReadResult(content) =>
//            assert(content != expected)
//          case _ =>
//            fail()
//    }
//
//  }
