package net.mem_memov.binet.memory

trait Content:

  def supplementBlocks(
    targetLength: Int
  ): Vector[Block]

  def write(
    contentIndex: Integer,
    blockIndex: UnsignedByte,
    block: Block
  ): Block

  def toAddress: Address