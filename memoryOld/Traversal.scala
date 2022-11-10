package net.mem_memov.binet.memoryOld

trait Traversal[T]:

  def nextTraversal(
    traversal: T
  ): Either[String, Option[(Address, T)]]

  extension (traversal: T)

    def next: Either[String, Option[(Address, T)]] =

      nextTraversal(traversal)
