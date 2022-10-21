package net.mem_memov.binet.memory.level

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.factory.defaultFactory._
import net.mem_memov.binet.memory.level.DefaultLevel

class DefaultLevelSuite extends munit.FunSuite:

  test("Level creates stores for keeping addresses") {

    given StockFactory with
      override def makeStock(size: Int, level: Level): Stock = fail("unexpected")

    val storeStub = new Store {
      override def write(destination: UnsignedByte, content: Address): Either[String, Store] = fail("unexpected")
      override def read(origin: UnsignedByte): Address = fail("unexpected")
      override def expand(minimumLength: Int): Store = fail("unexpected")
      override def padBig(content: Address): Either[String, Address] = fail("unexpected")
    }

    given StoreFactory with
      override def makeStore(size: Int): Store =
        assert(size == 6)
        storeStub

    val level = DefaultLevel(5)
    val store = level.createStore()

    assert(store == storeStub)
  }

  test("Level creates stocks for connection to child elements") {

    val stockStub = new Stock {
      override def write(index: UnsignedByte, destination: Address, content: Address): Either[String, Stock.Write] = fail("unexpected")
      override def read(index: UnsignedByte, origin: Address): Either[String, Address] = fail("unexpected")
    }

    given StockFactory with
      override def makeStock(size: Int, level: Level): Stock =
        assert(size == 256)
        stockStub

    given StoreFactory with
      override def makeStore(size: Int): Store = fail("unexpected")

    val level = DefaultLevel(3)

    val stock = level.createStock()

    assert(stock == stockStub)
  }
