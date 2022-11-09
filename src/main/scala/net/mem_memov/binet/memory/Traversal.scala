package net.mem_memov.binet.memory

trait Traversal[T]:

  def nextTraversal(
    traversal: T
  ): Either[String, Option[(Address, T)]]

  extension (traversal: T)

    def next: Either[String, Option[(Address, T)]] =

      nextTraversal(traversal)
