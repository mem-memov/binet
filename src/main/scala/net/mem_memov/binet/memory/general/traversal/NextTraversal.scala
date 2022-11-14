package net.mem_memov.binet.memory.general.traversal

trait NextTraversal[TRAVERSAL, ADDRESS]:

  def nextTraversalStep(
    traversal: TRAVERSAL
  ): Either[String, Option[(ADDRESS, TRAVERSAL)]]

  extension (traversal: TRAVERSAL)

    def nextTraversal(): Either[String, Option[(ADDRESS, TRAVERSAL)]] =

      nextTraversalStep(traversal)
