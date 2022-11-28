package net.mem_memov.binet.memory.store

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
  val b7 = UnsignedByte.fromInt(7)

  class TimmerStub

  given trimmerStub: TimmerStub = new TimmerStub

  test("Store can be written at the first index") {

    val writtenContent = Content(Vector(b5, b6))

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b4))
    ))

    given general.content.SupplementBlocks[Content, Block] with
      override def f(content: Content, targetLength: Int): Vector[Block] =
        assert(content.equals(writtenContent))
        assert(targetLength == 2)
        Vector()

    given general.content.Write[Content, Block] with
      override def f(content: Content, contentIndex: Integer, blockIndex: UnsignedByte, block: Block): Block =
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

    given TrimRight[TimmerStub, Block] with
      override def f(blocks: Vector[Block]): Vector[Block] =
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

    val writtenContent = Content(Vector(b5, b6))

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b4))
    ))

    given general.content.SupplementBlocks[Content, Block] with
      override def f(content: Content, targetLength: Int): Vector[Block] =
        assert(content.equals(writtenContent))
        assert(targetLength == 2)
        Vector()

    given general.content.Write[Content, Block] with
      override def f(content: Content, contentIndex: Integer, blockIndex: UnsignedByte, block: Block): Block =
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

    given TrimRight[TimmerStub, Block] with
      override def f(blocks: Vector[Block]): Vector[Block] =
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

  test("Store adds blocks when written with a larger content") {

    val writtenContent = Content(Vector(b5, b6, b7))

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b4))
    ))

    given general.content.SupplementBlocks[Content, Block] with
      override def f(content: Content, targetLength: Int): Vector[Block] =
        assert(content.equals(writtenContent))
        assert(targetLength == 2)
        Vector(Block(Vector(b0, b0)))

    given general.content.Write[Content, Block] with
      override def f(content: Content, contentIndex: Integer, blockIndex: UnsignedByte, block: Block): Block =
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
          case 2 =>
            assert(blockIndex == b0)
            assert(block.space == Vector(b0, b0))
            Block(Vector(b7, b0))
          case _ =>
            fail("unexpected")

    given TrimRight[TimmerStub, Block] with
      override def f(blocks: Vector[Block]): Vector[Block] =
        assert(blocks == Vector(
          Block(Vector(b5, b2)),
          Block(Vector(b6, b4)),
          Block(Vector(b7, b0))
        ))
        blocks

    val result = store.write(b0, writtenContent)

    assert(result == Store(Vector(
      Block(Vector(b5, b2)),
      Block(Vector(b6, b4)),
      Block(Vector(b7, b0))
    )))
  }

  test("Store gets trimmed on the side of most significant blocks") {

    val writtenContent = Content(Vector(b5, b6))

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b0)),
      Block(Vector(b4, b0))
    ))

    given general.content.SupplementBlocks[Content, Block] with
      override def f(content: Content, targetLength: Int): Vector[Block] =
        assert(content.equals(writtenContent))
        assert(targetLength == 3)
        Vector()

    given general.content.Write[Content, Block] with
      override def f(content: Content, contentIndex: Integer, blockIndex: UnsignedByte, block: Block): Block =
        assert(content.equals(writtenContent))
        contentIndex match
          case 0 =>
            assert(blockIndex == b0)
            assert(block.space == Vector(b1, b2))
            Block(Vector(b5, b2))
          case 1 =>
            assert(blockIndex == b0)
            assert(block.space == Vector(b3, b0))
            Block(Vector(b6, b0))
          case 2 =>
            assert(blockIndex == b0)
            assert(block.space == Vector(b4, b0))
            Block(Vector(b0, b0))
          case _ =>
            fail("unexpected")

    given TrimRight[TimmerStub, Block] with
      override def f(blocks: Vector[Block]): Vector[Block] =
        assert(blocks == Vector(
          Block(Vector(b5, b2)),
          Block(Vector(b6, b0)),
          Block(Vector(b0, b0))
        ))
        Vector(
          Block(Vector(b5, b2)),
          Block(Vector(b6, b0))
        )

    val result = store.write(b0, writtenContent)

    assert(result == Store(Vector(
      Block(Vector(b5, b2)),
      Block(Vector(b6, b0))
    )))
  }
