package net.mem_memov.binet.specific.block

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Block
import net.mem_memov.binet.memory.specific.Block.given

class IsEmptySuite extends munit.FunSuite:

  test("Block confirms being empty") {

    val block = Block(Vector(UnsignedByte.minimum, UnsignedByte.minimum))

    val result = block.isEmpty

    assert(result)
  }

  test("Block denies being empty") {

    val block = Block(Vector(UnsignedByte.minimum, UnsignedByte.maximum))

    val result = block.isEmpty

    assert(!result)
  }
