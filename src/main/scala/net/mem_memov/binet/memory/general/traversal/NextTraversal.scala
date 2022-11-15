package net.mem_memov.binet.memory.general.traversal

import net.mem_memov.binet.memory.general.address.{AddressIncrementer, AddressToPathConverter}

trait NextTraversal[TRAVERSAL]:

  def nextTraversalStep[
    ADDRESS : Ordering : AddressToPathConverter : AddressIncrementer
  ](
    traversal: TRAVERSAL
  ): Either[String, Option[(ADDRESS, TRAVERSAL)]]

  extension (traversal: TRAVERSAL)

    def nextTraversal[
      ADDRESS : Ordering : AddressToPathConverter : AddressIncrementer
    ](): Either[String, Option[(ADDRESS, TRAVERSAL)]] =

      nextTraversalStep(traversal)
