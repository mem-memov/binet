package net.mem_memov.binet.specific.store

import net.mem_memov.binet.memory.general.UnsignedByte
import net.mem_memov.binet.memory.specific.{Address, Block, Content, Store}
import net.mem_memov.binet.memory.specific.Store.given
import net.mem_memov.binet.memory.specific.store.general.trimmer.TrimRight

class WriteSuite extends munit.FunSuite:
  
  class Mock
  
  val b0 = UnsignedByte.fromInt(0)
  val b1 = UnsignedByte.fromInt(1)
  val b2 = UnsignedByte.fromInt(2)
  val b3 = UnsignedByte.fromInt(3)
  val b4 = UnsignedByte.fromInt(4)
  val b5 = UnsignedByte.fromInt(5)
  val b6 = UnsignedByte.fromInt(6)

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
    
    given TrimRight[Mock, Block] with
      override def trimBlocksRight(trimmer: Mock, blocks: Vector[Block]): Vector[Block] = 

    val result = store.write(b0, content)

    assert(result == expectedStore)
  }
