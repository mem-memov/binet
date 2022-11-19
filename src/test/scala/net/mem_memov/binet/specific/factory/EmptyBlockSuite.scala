package net.mem_memov.binet.specific.factory

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.Block
import net.mem_memov.binet.memory.specific.Factory
import net.mem_memov.binet.memory.specific.Factory.given

class EmptyBlockSuite extends munit.FunSuite:

  test("Factory creates an empty bock with all values at minimum") {

    val factory = new Factory

    val result = factory.emptyBlock()

    result.space.foreach(byte => assert(byte.atMinimum))

    assert(result.space.length == 256)
  }
