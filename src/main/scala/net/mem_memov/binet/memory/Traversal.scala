package net.mem_memov.binet.memory

trait Traversal:

  def next: Option[(Element, Traversal)]
