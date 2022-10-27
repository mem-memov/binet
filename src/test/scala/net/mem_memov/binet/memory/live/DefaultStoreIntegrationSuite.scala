package net.mem_memov.binet.memory.live

import net.mem_memov.binet.memory._
import net.mem_memov.binet.memory.live.defaultFactory._

class DefaultStoreIntegrationSuite extends munit.FunSuite:

  val factory = AddressFactory()
  
  test("Store keeps addresses at indices") {
    given AddressFactory = AddressFactory()
    given BlockFactory = BlockFactory()
    val blockFactory = BlockFactory()
    (
      for {
        size <- (1 to 10)
        store = DefaultStore(
          Vector.fill[Block](size)(blockFactory.emptyBlock)
        )
        destination <- (0 to 255).map(UnsignedByte.fromInt)
        content <- List(
          factory.makeAddress(List.fill(size)(UnsignedByte.maximum)),
          factory.makeAddress(List.fill(size)(UnsignedByte.maximum.decrement)),
          factory.makeAddress(List.fill(size)(UnsignedByte.minimum)),
          factory.makeAddress(List.fill(size)(UnsignedByte.minimum.increment)),
        )
      } yield (store, destination, content)
      ).foreach { case (store, destination, content) =>

      store.write(destination, content) match
        case Left(error) => fail(error)
        case Right(updatedStore) =>
          val result = updatedStore.read(destination)
          assert(result.isEqual(content))
    }
  }

