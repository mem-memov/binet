package net.mem_memov.binet.specific.block

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Block

class EmptyBlockSuite extends munit.FunSuite:

  test("Block can be created with all values at minimum") {

    val result = Block.emptyBlock

    result.space.foreach(byte => assert(byte.atMinimum))

    assert(result.space.length == 256)
  }
