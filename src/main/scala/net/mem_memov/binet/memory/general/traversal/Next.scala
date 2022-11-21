package net.mem_memov.binet.memory.general.traversal

import net.mem_memov.binet.memory.general.Step

trait Next[TRAVERSAL, ADDRESS]:

  def f(
    traversal: TRAVERSAL
  ): Either[String, Option[Step[ADDRESS, TRAVERSAL]]]

  extension (traversal: TRAVERSAL)

    def next(): Either[String, Option[Step[ADDRESS, TRAVERSAL]]] =

      f(traversal)
