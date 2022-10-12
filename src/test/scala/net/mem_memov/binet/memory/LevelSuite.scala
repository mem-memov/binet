package net.mem_memov.binet.memory

class LevelSuite extends munit.FunSuite:

  test("Level creates stores for keeping addresses") {
    val level = Level.top
    val store = level.createStore
    assert(store.isInstanceOf[Store])
  }

  test("Level creates stocks for connection to child elements") {
    val level = Level.top
    val stock = level.createStock
    assert(stock.isInstanceOf[Stock])
  }
