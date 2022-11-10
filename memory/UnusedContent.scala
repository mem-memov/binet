package net.mem_memov.binet.memory

import net.mem_memov.binet.memory.memory.general.UnsignedByte

trait UnusedContent(fail: String => Nothing) extends Content:

  override
  def supplementBlocks(
    targetLength: Int
  ): Vector[Block] =

    fail("unexpected")

  override
  def write(
    contentIndex: Integer,
    blockIndex: UnsignedByte,
    block: Block
  ): Block =

    fail("unexpected")

  override
  def toAddress: Address =

    fail("unexpected")
