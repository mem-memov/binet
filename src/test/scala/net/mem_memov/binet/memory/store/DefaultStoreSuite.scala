package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.factory.defaultFactory._
import net.mem_memov.binet.memory._

class DefaultStoreSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  test("Store writes addresses") {

    val destination = UnsignedByte.fromInt(5)
    val contentIndex = UnsignedByte.fromInt(11)

    val updatedBlock = new UnusedBlock(failMethod) {}

    val writableBlock = new UnusedBlock(failMethod) {
      override def write(position: UnsignedByte, content: UnsignedByte): Block =
        assert(position == destination)
        assert(content == contentIndex)
        updatedBlock
    }

    val content = new UnusedAddress(failMethod):
      override def zipIndices(elements: Vector[Block]): Either[String, Vector[(UnsignedByte, Block)]] =
        assert(elements(0) == writableBlock)
        Right(Vector(contentIndex -> writableBlock))

    val updatedStore = new UnusedStore(failMethod) {}

    def updateWithBlocks(updatedBlocks: Vector[Block]): Store =
      assert(updatedBlocks(0) == updatedBlock)
      updatedStore

    for {
      result <- DefaultStore.write(destination, content, Vector(writableBlock), updateWithBlocks)
    } yield assert(result == updatedStore)
  }

  test("Store reads addresses") {

    val origin = UnsignedByte.fromInt(5)
    val contentIndex = UnsignedByte.fromInt(11)

    val readableBlock = new UnusedBlock(failMethod):
      override def read(position: UnsignedByte): UnsignedByte =
        assert(position == origin)
        contentIndex

    val readAddress = new UnusedAddress(failMethod):
      override lazy val indices: List[UnsignedByte] =
        List(contentIndex)

    val addressFactory = new UnusedAddressFactory(failMethod):
      override def makeAddress(indices: List[UnsignedByte]): Address =
        assert(indices.head == contentIndex)
        readAddress

    val result = DefaultStore.read(origin, Vector(readableBlock), addressFactory)
    assert(result == readAddress)
  }

  test("Store gets expanded") {

    val oldBlock = new UnusedBlock(failMethod) {}
    val newBlock = new UnusedBlock(failMethod) {}

    val blockFactory = new BlockFactory:
      override lazy val emptyBlock: Block = newBlock

    val originalStore = new UnusedStore(failMethod) {}
    val updatedStore = new UnusedStore(failMethod) {}

    def updateWithBlocks(updatedBlocks: Vector[Block]): Store =
      assert(updatedBlocks(0) == newBlock && updatedBlocks(1) == oldBlock)
      updatedStore

    val result = DefaultStore.expand(2, Vector(oldBlock), blockFactory, updateWithBlocks, originalStore)

    assert(result == updatedStore)
  }

  test("Store doesn't get expanded if already at desired length") {

    val oldBlock = new UnusedBlock(failMethod) {}

    val blockFactory = new BlockFactory:
      override lazy val emptyBlock: Block = fail("unexpected")

    val originalStore = new UnusedStore(failMethod) {}

    def updateWithBlocks(updatedBlocks: Vector[Block]): Store = fail("unexpected")

    val result = DefaultStore.expand(1, Vector(oldBlock), blockFactory, updateWithBlocks, originalStore)

    assert(result == originalStore)
  }

  test("Store doesn't get expanded if greater than desired length") {

    val firstBlock = new UnusedBlock(failMethod) {}
    val secondBlock = new UnusedBlock(failMethod) {}

    val blockFactory = new BlockFactory:
      override lazy val emptyBlock: Block = fail("unexpected")

    val originalStore = new UnusedStore(failMethod) {}

    def updateWithBlocks(updatedBlocks: Vector[Block]): Store = fail("unexpected")

    val result = DefaultStore.expand(1, Vector(firstBlock, secondBlock), blockFactory, updateWithBlocks, originalStore)

    assert(result == originalStore)
  }

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

