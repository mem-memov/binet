package net.mem_memov.binet.memory

class StoreSuite extends munit.FunSuite:

  test("Store keeps addresses at indices") {
    (
      for {
        size <- (1 to 10)
        store = Store(
          Vector.fill[Block](size)(Block())
        )
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          Address.apply(List.fill(size)(UnsignedByte.maximum)),
          Address.apply(List.fill(size)(UnsignedByte.maximum.decrement)),
          Address.apply(List.fill(size)(UnsignedByte.minimum)),
          Address.apply(List.fill(size)(UnsignedByte.minimum.increment)),
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
        store = Store(
          Vector.fill[Block](size)(Block())
        )
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          Address.apply(List.fill(size + 1)(UnsignedByte.maximum)),
          Address.apply(List.fill(size + 2)(UnsignedByte.maximum.decrement)),
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
        store = Store(
          Vector.fill[Block](size)(Block())
        )
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          Address.apply(List.fill(size - 1)(UnsignedByte.maximum)),
          Address.apply(List.fill(size - 2)(UnsignedByte.maximum.decrement)),
        )
      } yield (store, destination, content)
      ).foreach { case (store, destination, content) =>

      store.write(destination, content) match
        case Left(error) => assert(error == "Destination not written: content has wrong number of indices")
        case Right(updatedStore) => fail("An error expected")
    }
  }
