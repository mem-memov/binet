package net.mem_memov.binet.memory.general.traversal

trait Next[TRAVERSAL, ADDRESS]:

  def f(
    traversal: TRAVERSAL
  ): Either[String, Option[(ADDRESS, TRAVERSAL)]]

  extension (traversal: TRAVERSAL)

    def nextTraversal(): Either[String, Option[(ADDRESS, TRAVERSAL)]] =

      f(traversal)
