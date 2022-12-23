package net.mem_memov.binet.hexagon.general.network

trait ReadArrow[NETWORK, ARROW, ARROW_REFERENCE]:

  def f(
    network: NETWORK,
    arrowReference: ARROW_REFERENCE
  ): Either[String, ARROW]

  extension (network: NETWORK)

    def readArrow(
      arrowReference: ARROW_REFERENCE
    ): Either[String, ARROW] =

      f(network, arrowReference)
