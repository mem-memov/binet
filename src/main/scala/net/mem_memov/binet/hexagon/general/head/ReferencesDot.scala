package net.mem_memov.binet.hexagon.general.head

trait ReferencesDot[HEAD, DOT_REFERENCE]:

  def f(
    head: HEAD,
    dotReference: DOT_REFERENCE
  ): Boolean

  extension (head: HEAD)

    def referencesDot(
      dotReference: DOT_REFERENCE
    ): Boolean =

      f(head, dotReference)
