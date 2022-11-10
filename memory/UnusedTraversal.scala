package net.mem_memov.binet.memory

trait UnusedTraversal(fail: String => Nothing) extends Traversal:

  def next: Either[String, Option[(Address, Traversal)]] =

    fail("unexpected")
