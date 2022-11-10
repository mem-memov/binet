package net.mem_memov.binet.memoryOld

import net.mem_memov.binet.memory.memory.general.UnsignedByte

trait Content[C, A : Address, B : Block]:

  def supplementContentBlocks(
    content: C,
    targetLength: Int
  ): Vector[B]

  def writeContent(
    content: C,
    contentIndex: Integer,
    blockIndex: UnsignedByte,
    block: B
  ): B

  def contentToAddress(
    content: C
  ): A

  extension (content: C)

    def supplementBlocks(
      targetLength: Int
    ): Vector[B] =

      supplementContentBlocks(content, targetLength)

    def write(
      contentIndex: Integer,
      blockIndex: UnsignedByte,
      block: B
    ): B =

      writeContent(content, contentIndex, blockIndex, block)

    def toAddress: A =

      contentToAddress(content)