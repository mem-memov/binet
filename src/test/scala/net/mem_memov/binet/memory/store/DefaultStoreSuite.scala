package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.{Address, Block, Store, UnsignedByte}

class DefaultStoreSuite extends munit.FunSuite:

  test("Store keeps addresses at indices") {
    (
      for {
        size <- (1 to 10)
        store = DefaultStore(
          Vector.fill[Block](size)(DefaultBlock.empty)
        )
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          DefaultAddress(List.fill(size)(UnsignedByte.maximum)),
          DefaultAddress(List.fill(size)(UnsignedByte.maximum.decrement)),
          DefaultAddress(List.fill(size)(UnsignedByte.minimum)),
          DefaultAddress(List.fill(size)(UnsignedByte.minimum.increment)),
        )
      } yield (store, destination, content)
      ).foreach { case (store, destination, content) =>

      store.write(destination, content) match
        case Left(error) => fail(error)
        case Right(updatedStore) =>
          val result = updatedStore.read(destination)
          assert(result == content)
    }
  }

  test("Store rejects addresses that are too large (too many indices)") {
    (
      for {
        size <- (1 to 10)
        store = DefaultStore(
          Vector.fill[Block](size)(DefaultBlock.empty)
        )
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          DefaultAddress(List.fill(size + 1)(UnsignedByte.maximum)),
          DefaultAddress(List.fill(size + 2)(UnsignedByte.maximum.decrement)),
        )
      } yield (store, destination, content)
      ).foreach { case (store, destination, content) =>

      store.write(destination, content) match
        case Left(error) => assert(error == "Destination not written: content has wrong number of indices")
        case Right(updatedStore) => fail("An error expected")
    }
  }

  test("Store rejects addresses that are too small (to few indices)") {
    (
      for {
        size <- (2 to 10)
        store = DefaultStore(
          Vector.fill[Block](size)(DefaultBlock.empty)
        )
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          DefaultAddress(List.fill(size - 1)(UnsignedByte.maximum)),
          DefaultAddress(List.fill(size - 2)(UnsignedByte.maximum.decrement)),
        )
      } yield (store, destination, content)
      ).foreach { case (store, destination, content) =>

      store.write(destination, content) match
        case Left(error) => assert(error == "Destination not written: content has wrong number of indices")
        case Right(updatedStore) => fail("An error expected")
    }
  }
