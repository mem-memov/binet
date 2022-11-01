package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeStore.UnusedTrimmer

class TreeStoreUnitSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  val trimmer = new UnusedTrimmer(failMethod) {}

  test("Store writes addresses") {

    val destination = UnsignedByte.fromInt(5)
    val contentIndex = UnsignedByte.fromInt(11)

    val updatedBlock = new UnusedBlock(failMethod) {}

    val block = new UnusedBlock(failMethod) {
      override def write(position: UnsignedByte, content: UnsignedByte): Block =
        assert(position == destination)
        assert(content == contentIndex)
        updatedBlock
    }

    val content = new UnusedAddress(failMethod):
      override def zipIndices(elements: Vector[Block]): Either[String, Vector[(UnsignedByte, Block)]] =
        assert(elements(0) == block)
        Right(Vector(contentIndex -> block))

    given AddressFactory = new UnusedAddressFactory(failMethod) {}
    given BlockFactory = new UnusedBlockFactory(failMethod) {}

    val store = TreeStore(Vector(block), trimmer)

    for {
      result <- store.write(destination, content)
    } yield assert(result.blocks(0) == updatedBlock)
  }

  test("Store reads addresses") {

    val origin = UnsignedByte.fromInt(5)
    val contentIndex = UnsignedByte.fromInt(11)

    val block = new UnusedBlock(failMethod):
      override def read(position: UnsignedByte): UnsignedByte =
        assert(position == origin)
        contentIndex

    val readAddress = new UnusedAddress(failMethod):
      override lazy val indices: List[UnsignedByte] =
        List(contentIndex)

    given AddressFactory = new UnusedAddressFactory(failMethod):
      override def makeAddress(indices: List[UnsignedByte]): Address =
        assert(indices.head == contentIndex)
        readAddress

    given BlockFactory = new UnusedBlockFactory(failMethod) {}

    val store = TreeStore(Vector(block), trimmer)

    val result = store.read(origin)
    assert(result == readAddress)
  }

  test("Store gets expanded") {

    val oldBlock = new UnusedBlock(failMethod) {}
    val newBlock = new UnusedBlock(failMethod) {}

    given AddressFactory = new UnusedAddressFactory(failMethod) {}

    given BlockFactory = new UnusedBlockFactory(failMethod):
      override lazy val emptyBlock: Block = newBlock

    val store = TreeStore(Vector(oldBlock), trimmer)

    val result = store.expand(2)

    assert(result.blocks(0) == newBlock && result.blocks(1) == oldBlock)
  }

  test("Store doesn't get expanded if already at desired length") {

    val oldBlock = new UnusedBlock(failMethod) {}

    given AddressFactory = new UnusedAddressFactory(failMethod) {}
    given BlockFactory = new UnusedBlockFactory(failMethod) {}

    val store = TreeStore(Vector(oldBlock), trimmer)

    val result = store.expand(1)

    assert(result == store)
  }

  test("Store doesn't get expanded if greater than desired length") {

    val firstBlock = new UnusedBlock(failMethod) {}
    val secondBlock = new UnusedBlock(failMethod) {}

    given AddressFactory = new UnusedAddressFactory(failMethod) {}
    given BlockFactory = new UnusedBlockFactory(failMethod) {}

    val store = TreeStore(Vector(firstBlock, secondBlock), trimmer)

    val result = store.expand(1)

    assert(result == store)
  }


