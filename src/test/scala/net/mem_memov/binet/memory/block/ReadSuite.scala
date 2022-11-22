package net.mem_memov.binet.memory.block

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Block
import net.mem_memov.binet.memory.specific.Block.given

class ReadSuite extends munit.FunSuite:

  test("Block reads at the least significant position") {

    val block = Block(Vector(UnsignedByte.minimum, UnsignedByte.maximum))

    val result = block.read(UnsignedByte.fromInt(0))

    assert(result == UnsignedByte.minimum)
  }

  test("Block reads at the most significant position") {

    val block = Block(Vector(UnsignedByte.minimum, UnsignedByte.maximum))

    val result = block.read(UnsignedByte.fromInt(1))

    assert(result == UnsignedByte.maximum)
  }
