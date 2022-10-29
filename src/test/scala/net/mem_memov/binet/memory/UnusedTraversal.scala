package net.mem_memov.binet.memory

trait UnusedTraversal(fail: String => Nothing) extends Traversal:

  override def next: Option[(Element, Traversal)] =

    fail("unexpected")
