package net.mem_memov.binet.memory.tree.defaultFactory

import net.mem_memov.binet.memory.*

trait UnusedElementFactory(fail: String => Nothing) extends ElementFactory:

  override lazy val emptyElement: Element =

    fail("unexpected")
