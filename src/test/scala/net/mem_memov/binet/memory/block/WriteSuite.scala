package net.mem_memov.binet.memory.block

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Block
import net.mem_memov.binet.memory.specific.Block.given

class WriteSuite extends munit.FunSuite:

  test("Block writes at the least significant position") {

    val block = Block(Vector(UnsignedByte.minimum, UnsignedByte.maximum))

    val result = block.write(UnsignedByte.fromInt(0), UnsignedByte.fromInt(222))

    assert(result == Block(Vector(UnsignedByte.fromInt(222), UnsignedByte.maximum)))
  }

  test("Block writes at the most significant position") {

    val block = Block(Vector(UnsignedByte.minimum, UnsignedByte.maximum))

    val result = block.write(UnsignedByte.fromInt(1), UnsignedByte.fromInt(222))

    assert(result == Block(Vector(UnsignedByte.minimum, UnsignedByte.fromInt(222))))
  }
