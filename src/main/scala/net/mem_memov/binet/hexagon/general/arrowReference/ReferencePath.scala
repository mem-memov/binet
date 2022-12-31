package net.mem_memov.binet.hexagon.general.arrowReference

trait ReferencePath[ARROW_REFERENCE, DOT_REFERENCE, NETWORK]:

  def f(
    arrowReference: ARROW_REFERENCE,
    dotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW_REFERENCE)]

  extension (arrowReference: ARROW_REFERENCE)

    def referencePath(
      dotReference: DOT_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW_REFERENCE)] =

      f(arrowReference, dotReference, network)
