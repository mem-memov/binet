package net.mem_memov.binet.memory

import zio.test._

class BlockSuite extends ZIOSpecDefault:

  def spec = test("Read a byte from a block") {
    val block = Block()
    val zio = (
      for {
        position <- Byte.MinValue to Byte.MaxValue
        content <- Byte.MinValue to Byte.MaxValue
      } yield (UnsignedByte(position.toByte), UnsignedByte(content.toByte))
    ).foreach {
      case (position, content) =>
        for {
          _ <- block.write(position, content)
          result <- block.read(position)
        } yield result == content
    }
    assertZIO()
  }
