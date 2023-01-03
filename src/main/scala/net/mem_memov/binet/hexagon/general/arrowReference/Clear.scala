package net.mem_memov.binet.hexagon.general.arrowReference

trait Clear[ARROW_REFERENCE, NETWORK]:

  def f(
    arrowReference: ARROW_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW_REFERENCE)]

  extension (arrowReference: ARROW_REFERENCE)

    def clear(
      network: NETWORK
    ): Either[String, (NETWORK, ARROW_REFERENCE)] =

      f(arrowReference, network)
