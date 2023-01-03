package net.mem_memov.binet.hexagon.general.tail

trait GetReferencedBy[TAIL, ARROW_REFERENCE, NETWORK]:

  def f(
    tail: TAIL,
    arrowReference: ARROW_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW_REFERENCE)]

  extension (tail: TAIL)

    def getReferencedBy(
      arrowReference: ARROW_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW_REFERENCE)] =

      f(tail, arrowReference, network)
