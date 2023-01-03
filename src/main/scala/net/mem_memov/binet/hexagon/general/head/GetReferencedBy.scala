package net.mem_memov.binet.hexagon.general.head

trait GetReferencedBy[HEAD, ARROW_REFERENCE, NETWORK]:

  def f(
    head: HEAD,
    arrowReference: ARROW_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW_REFERENCE)]

  extension (head: HEAD)

    def getReferencedBy(
      arrowReference: ARROW_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW_REFERENCE)] =

      f(head, arrowReference, network)
