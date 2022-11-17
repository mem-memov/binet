package net.mem_memov.binet.specific.content

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Block, Content}
import net.mem_memov.binet.memory.specific.Content.given

class SupplementContentBlocksSuite extends munit.FunSuite:

  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)
  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)
  val b4 = UnsignedByte.fromInt(4)
  val b5 = UnsignedByte.fromInt(5)

  test("Content produces missing blocks") {

    val content = Content(Vector(b1, b2, b3, b4, b5))

    val result = content.supplementBlocks(2)

    assert(result == Vector(
      Block.emptyBlock,
      Block.emptyBlock,
      Block.emptyBlock
    ))
  }

  test("Content produces no blocks if it has the same length") {

    val content = Content(Vector(b1, b2, b3, b4, b5))

    val result = content.supplementBlocks(5)

    assert(result.isEmpty)
  }

  test("Content produces no blocks if it is smaller") {

    val content = Content(Vector(b1, b2, b3))

    val result = content.supplementBlocks(5)

    assert(result.isEmpty)
  }
