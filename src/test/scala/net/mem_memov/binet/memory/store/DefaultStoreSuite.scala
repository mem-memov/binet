package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.factory.defaultFactory.{AddressFactory, BlockFactory}
import net.mem_memov.binet.memory.{Address, Block, Store, UnsignedByte, ZippingAddress}

class DefaultStoreSuite extends munit.FunSuite:

  test("Store writes addresses") {

    given AddressFactory = new AddressFactory:
      override def makeAddress(indices: List[UnsignedByte]): Address = fail("unexpected")
      override lazy val zeroAddress: Address = fail("unexpected")

    given BlockFactory = new BlockFactory:
      override lazy val emptyBlock: Block = fail("unexpected")

    val store = DefaultStore(Vector(DefaultBlock.empty))

    val content = new ZippingAddress {
      override def zipIndices[A](elements: Vector[A]): Either[String, Vector[(UnsignedByte, A)]] = ???
    }

//    store.write()
  }

  test("Store keeps addresses at indices") {
    given AddressFactory = AddressFactory()
    given BlockFactory = BlockFactory()
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

