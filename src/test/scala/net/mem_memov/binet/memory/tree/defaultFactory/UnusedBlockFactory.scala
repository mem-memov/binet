package net.mem_memov.binet.memory.tree.defaultFactory

import net.mem_memov.binet.memory.*

trait UnusedBlockFactory(fail: String => Nothing) extends BlockFactory:

  override lazy val emptyBlock: Block =

    fail("unexpected")