package net.mem_memov.binet.memory.tree

import net.mem_memov.binet.memory.*
import net.mem_memov.binet.memory.memory.general.UnsignedByte
import net.mem_memov.binet.memory.tree.treeFactory.*
import net.mem_memov.binet.memory.tree.treeStore.UnusedTrimmer

class TreeStoreUnitSuite extends munit.FunSuite:

  def failMethod(message: String): Nothing = fail(message)

  val trimmer = new UnusedTrimmer(failMethod) {}

  test("Store writes addresses") {

    val destinationIndex = UnsignedByte.fromInt(5)
    val contentIndex = UnsignedByte.fromInt(11)
    val updatedBlock = new UnusedBlock(failMethod) {}

    val originalBlock = new UnusedBlock(failMethod) {}

    val writtenContent = new UnusedContent(failMethod):
      override def supplementBlocks(targetLength: Int): Vector[Block] =
        assert(targetLength == 1)
        Vector()
      override def write(contentIndex: Integer, blockIndex: UnsignedByte, block: Block): Block =
        assert(contentIndex == 0)
        assert(blockIndex == destinationIndex)
        assert(block.equals(originalBlock))
        updatedBlock

    val trimmer = new UnusedTrimmer(failMethod):
      override def trimRight(blocks: Vector[Block]): Vector[Block] =
        assert(blocks(0) == updatedBlock)
        Vector(updatedBlock)

    given AddressFactory = new UnusedAddressFactory(failMethod) {}
    given BlockFactory = new UnusedBlockFactory(failMethod) {}

    val store = TreeStore(Vector(originalBlock), trimmer)

    val result = store.write(destinationIndex, writtenContent)
    assert(result.blocks(0) == updatedBlock)
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


