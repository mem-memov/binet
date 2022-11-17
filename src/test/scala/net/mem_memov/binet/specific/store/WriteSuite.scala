package net.mem_memov.binet.specific.store

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.specific.{Address, Block, Content, Store}
import net.mem_memov.binet.memory.specific.Store.given
import net.mem_memov.binet.memory.specific.store.general.trimmer.TrimRight
import net.mem_memov.binet.memory.specific.store.specific.Trimmer
import net.mem_memov.binet.memory.specific.store.specific.Trimmer.given

class WriteSuite extends munit.FunSuite:

  class Stub

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)
  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)
  val b4 = UnsignedByte.fromInt(4)
  val b5 = UnsignedByte.fromInt(5)
  val b6 = UnsignedByte.fromInt(6)

  given trimmer: Stub = new Stub

  test("Store can be written at the first index") {

    val content = Content(Vector(UnsignedByte.fromInt(5), UnsignedByte.fromInt(6)))

    val store = Store(Vector(
      Block(Vector(b1, b2)),
      Block(Vector(b3, b4))
    ))

    val expectedStore = Store(Vector(
      Block(Vector(b5, b2)),
      Block(Vector(b6, b4))
    ))

    given TrimRight[Stub, Block] with
      override def trimBlocksRight(trimmer: Stub, blocks: Vector[Block]): Vector[Block] =
        blocks

    given general.content.SupplementBlocks[Content, Block] with
      override def supplementContentBlocks(content: Content, targetLength: Int): Vector[Block] =
        Vector()

    given general.content.Write[Content, Block] with
      override def writeContent(content: Content, contentIndex: Integer, blockIndex: UnsignedByte, block: Block): Block =
        contentIndex match
          case 0 => Block(Vector(b5, b2))
          case 1 => Block(Vector(b6, b4))
          case _ => fail("unexpected")

    val result = store.write(b0, content)

    assert(result == expectedStore)
  }
