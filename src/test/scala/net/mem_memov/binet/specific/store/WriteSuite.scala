package net.mem_memov.binet.specific.store

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.{Address, Block, Content, Store}
import net.mem_memov.binet.memory.specific.Store.given
import net.mem_memov.binet.memory.specific.store.general.trimmer.TrimRight
import net.mem_memov.binet.memory.specific.store.specific.Trimmer
import net.mem_memov.binet.memory.specific.store.specific.Trimmer.given

class WriteSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)
  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)
  val b4 = UnsignedByte.fromInt(4)
  val b5 = UnsignedByte.fromInt(5)
  val b6 = UnsignedByte.fromInt(6)

  class Stub

  given trimmer: Stub = new Stub

  test("Store can be written at the first index") {

    val writtenContent = Content(Vector(UnsignedByte.fromInt(5), UnsignedByte.fromInt(6)))

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b4))
    ))

    given general.content.SupplementBlocks[Content, Block] with
      override def supplementContentBlocks(content: Content, targetLength: Int): Vector[Block] =
        assert(content.equals(writtenContent))
        assert(targetLength == 2)
        Vector()

    given general.content.Write[Content, Block] with
      override def writeContent(content: Content, contentIndex: Integer, blockIndex: UnsignedByte, block: Block): Block =
        assert(content.equals(writtenContent))
        contentIndex match
          case 0 =>
            assert(blockIndex == b0)
            assert(block.space == Vector(b1, b2))
            Block(Vector(b5, b2))
          case 1 =>
            assert(blockIndex == b0)
            assert(block.space == Vector(b3, b4))
            Block(Vector(b6, b4))
          case _ =>
            fail("unexpected")

    given TrimRight[Stub, Block] with
      override def trimBlocksRight(trimmer: Stub, blocks: Vector[Block]): Vector[Block] =
        assert(blocks == Vector(
          Block(Vector(b5, b2)),
          Block(Vector(b6, b4))
        ))
        blocks

    val result = store.write(b0, writtenContent)

    assert(result == Store(Vector(
      Block(Vector(b5, b2)),
      Block(Vector(b6, b4))
    )))
  }

  test("Store can be written at the last index") {

    val writtenContent = Content(Vector(UnsignedByte.fromInt(5), UnsignedByte.fromInt(6)))

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b4))
    ))

    given general.content.SupplementBlocks[Content, Block] with
      override def supplementContentBlocks(content: Content, targetLength: Int): Vector[Block] =
        assert(content.equals(writtenContent))
        assert(targetLength == 2)
        Vector()

    given general.content.Write[Content, Block] with
      override def writeContent(content: Content, contentIndex: Integer, blockIndex: UnsignedByte, block: Block): Block =
        assert(content.equals(writtenContent))
        contentIndex match
          case 0 =>
            assert(blockIndex == b1)
            assert(block.space == Vector(b1, b2))
            Block(Vector(b1, b5))
          case 1 =>
            assert(blockIndex == b1)
            assert(block.space == Vector(b3, b4))
            Block(Vector(b3, b6))
          case _ =>
            fail("unexpected")

    given TrimRight[Stub, Block] with
      override def trimBlocksRight(trimmer: Stub, blocks: Vector[Block]): Vector[Block] =
        assert(blocks == Vector(
          Block(Vector(b1, b5)),
          Block(Vector(b3, b6))
        ))
        blocks

    val result = store.write(b1, writtenContent)

    assert(result == Store(Vector(
      Block(Vector(b1, b5)),
      Block(Vector(b3, b6))
    )))
  }


