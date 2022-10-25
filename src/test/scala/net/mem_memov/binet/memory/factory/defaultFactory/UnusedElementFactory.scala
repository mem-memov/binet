package net.mem_memov.binet.memory.factory.defaultFactory

import net.mem_memov.binet.memory._

trait UnusedElementFactory(fail: String => Nothing) extends ElementFactory:

  override lazy val emptyElement: Element =

    fail("unexpected")
