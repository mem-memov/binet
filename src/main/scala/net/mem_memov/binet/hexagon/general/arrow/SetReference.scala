package net.mem_memov.binet.hexagon.general.arrow

trait SetReference[ARROW, ARROW_REFERENCE, NETWORK]:

  def f(
    arrow: ARROW,
    arrowReference: ARROW_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW_REFERENCE)]

  extension (arrow: ARROW)

    def setReference(
      arrowReference: ARROW_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW_REFERENCE)] =

      f(arrow, arrowReference, network)
