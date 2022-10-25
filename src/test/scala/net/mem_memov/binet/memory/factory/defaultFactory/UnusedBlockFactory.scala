package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._

trait UnusedBlockFactory(fail: String => Nothing) extends BlockFactory:

  override lazy val emptyBlock: Block =

    fail("unexpected")