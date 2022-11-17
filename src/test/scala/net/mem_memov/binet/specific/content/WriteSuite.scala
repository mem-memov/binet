package net.mem_memov.binet.specific.content

import net.mem_memov.binet.memory.general
import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Block, Content}
import net.mem_memov.binet.memory.specific.Content.given

class WriteSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)
  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)
  val b4 = UnsignedByte.fromInt(4)
  val b5 = UnsignedByte.fromInt(5)

  test("Content writes its part at a position of a block") {

    val content = Content(Vector(b3, b4, b5))

    val originalBlock = Block(Vector(b0, b0, b0, b0))
    val expectedBlock = Block(Vector(b0, b0, b0, b4))

    given general.block.Write[Block] with
      override def writeBlock(block: Block, position: UnsignedByte, content: UnsignedByte): Block =
        assert(block.equals(originalBlock))
        assert(position == b3)
        assert(content == b4)
        expectedBlock

    val result = content.write(1, b3, originalBlock)

    assert(result.equals(expectedBlock))
  }

  test("Content writes zero at a position of a block") {

    val content = Content(Vector(b3, b4, b5))

    val originalBlock = Block(Vector(b1, b1, b1, b1))
    val expectedBlock = Block(Vector(b1, b1, b1, b0))

    given general.block.Write[Block] with
      override def writeBlock(block: Block, position: UnsignedByte, content: UnsignedByte): Block =
        assert(block.equals(originalBlock))
        assert(position == b3)
        assert(content == b0)
        expectedBlock

    val result = content.write(17, b3, originalBlock)

    assert(result.equals(expectedBlock))
  }
