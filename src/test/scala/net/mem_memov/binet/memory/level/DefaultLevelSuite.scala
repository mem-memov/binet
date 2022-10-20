package net.mem_memov.binet.memory.level

import net.mem_memov.binet.memory.{Level, Stock, UnsignedByte}
import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.level.DefaultLevel

class DefaultLevelSuite extends munit.FunSuite:

  test("Level creates stores for keeping addresses") {
    val level = DefaultLevel.top
    val store = level.createStore()

    val destination = UnsignedByte.fromInt(7)
    val content = DefaultAddress(List(UnsignedByte.fromInt(32)))
    store.write(destination, content) match {
      case Left(error) => fail(error)
      case Right(updatedStore) =>
        val result = updatedStore.read(destination)
        assert(result == content)
    }
  }

  test("Level creates stocks for connection to child elements") {
    val level = DefaultLevel.top
    val stock = level.createStock()
    assert(stock.isInstanceOf[Stock])
  }
