package net.mem_memov.binet.memory

class BlockSuite extends munit.FunSuite:

  test("Read a byte from a block") {
    val block = Block()
    (
      for {
        position <- Byte.MinValue to Byte.MaxValue
        content <- Byte.MinValue to Byte.MaxValue
      } yield (UnsignedByte(position.toByte), UnsignedByte(content.toByte))
    ).foreach {
      case (position, content) =>
        val writtenBlock = block.write(position, content)
        val result = writtenBlock.read(position)
        assert(result == content)
    }
  }
