package net.mem_memov.binet.memory.content

import net.mem_memov.binet.memory.general
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

  class FactoryStub
  given factoryStub: FactoryStub = new FactoryStub

  class BlockStub
  given emptyBlockStub: BlockStub = new BlockStub

  test("Content produces missing blocks") {

    val content = Content(Vector(b1, b2, b3, b4, b5))

    given general.factory.EmptyBlock[FactoryStub, BlockStub] with
      override def f(): BlockStub =
        emptyBlockStub

    val result = content.supplementBlocks(2)

    assert(result == Vector(
      emptyBlockStub,
      emptyBlockStub,
      emptyBlockStub
    ))
  }

  test("Content produces no blocks if it has the same length") {

    val content = Content(Vector(b1, b2, b3, b4, b5))

    given general.factory.EmptyBlock[FactoryStub, BlockStub] with
      override def f(): BlockStub =
        fail("unexpected")

    val result = content.supplementBlocks(5)

    assert(result.isEmpty)
  }

  test("Content produces no blocks if it is smaller") {

    val content = Content(Vector(b1, b2, b3))

    given general.factory.EmptyBlock[FactoryStub, BlockStub] with
      override def f(): BlockStub =
        fail("unexpected")

    val result = content.supplementBlocks(5)

    assert(result.isEmpty)
  }
