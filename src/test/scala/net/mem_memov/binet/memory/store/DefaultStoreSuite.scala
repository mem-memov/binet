package net.mem_memov.binet.memory.store

import net.mem_memov.binet.memory.block.DefaultBlock
import net.mem_memov.binet.memory.address.DefaultAddress
import net.mem_memov.binet.memory.factory.defaultFactory._
import net.mem_memov.binet.memory._

class DefaultStoreSuite extends munit.FunSuite:

  given AddressFactory = new AddressFactory:
    override def makeAddress(indices: List[UnsignedByte]): Address = fail("unexpected")
    override lazy val zeroAddress: Address = fail("unexpected")

  given BlockFactory = new BlockFactory:
    override lazy val emptyBlock: Block = fail("unexpected")

  val unusedBlock: Block = new Block {
    override def read(position: UnsignedByte): UnsignedByte = fail("unexpected")
    override def write(position: UnsignedByte, content: UnsignedByte): Block = fail("unexpected")
  }

  test("Store writes addresses") {

    val destination = UnsignedByte.fromInt(5)
    val contentIndex = UnsignedByte.fromInt(11)

    val block = new Block:
      override def read(position: UnsignedByte): UnsignedByte = fail("unexpected")
      override def write(position: UnsignedByte, content: UnsignedByte): Block =
        assert(position == destination)
        assert(content == contentIndex)
        unusedBlock

    val store = DefaultStore(Vector(block))

    val content = new ZippingAddress:
      override def zipIndices(elements: Vector[WritableBlock]): Either[String, Vector[(UnsignedByte, WritableBlock)]] =
        assert(elements == Vector(block))
        Right(Vector(contentIndex -> block))

    for {
      result <- store.write(destination, content)
    } yield assert(result.blocks == Vector(unusedBlock))
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

