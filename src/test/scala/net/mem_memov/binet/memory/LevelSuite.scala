package net.mem_memov.binet.memory

class LevelSuite extends munit.FunSuite:

  test("Level creates stores for keeping addresses") {
    val level = Level.top
    val store = level.createStore()

    val destination = UnsignedByte.fromInt(7)
    val content = Address.apply(List(UnsignedByte.fromInt(32)))
    store.write(destination, content) match {
      case Left(error) => fail(error)
      case Right(updatedStore) =>
        val result = updatedStore.read(destination)
        assert(result == content)
    }
  }

  test("Level creates stocks for connection to child elements") {
    val level = Level.top
    val stock = level.createStock()
    assert(stock.isInstanceOf[Stock])
  }
