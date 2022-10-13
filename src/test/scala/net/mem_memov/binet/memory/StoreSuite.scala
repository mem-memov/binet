package net.mem_memov.binet.memory

class StoreSuite extends munit.FunSuite:

  test("Store keeps addresses at indices") {
    (
      for {
        size <- (1 to 10)
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          Address.apply(List.fill(size)(UnsignedByte.maximum)),
          Address.apply(List.fill(size)(UnsignedByte.maximum.decrement)),
          Address.apply(List.fill(size)(UnsignedByte.minimum)),
          Address.apply(List.fill(size)(UnsignedByte.minimum.increment)),
        )
      } yield (size, destination, content)
    ).foreach { case (size, destination, content) =>

      val store = new Store(
        Vector.fill[Block](size)(Block())
      )

      store.write(destination, content) match
        case Left(error) => fail(error)
        case Right(updatedStore) =>
          val result = updatedStore.read(destination)
          assert(result == content)
    }

  }
