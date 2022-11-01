package net.mem_memov.binet.memory

trait Traversal:

  def next: Either[String, Option[(Address, Traversal)]]
