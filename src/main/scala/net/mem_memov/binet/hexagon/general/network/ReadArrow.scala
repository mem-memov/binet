package net.mem_memov.binet.hexagon.general.network

trait ReadArrow[NETWORK, ARROW, ARROW_REFERENCE]:

  def f(
    network: NETWORK,
    arrowReference: ARROW_REFERENCE
  ): Either[String, Option[ARROW]]

  extension (network: NETWORK)

    def readArrow(
      arrowReference: ARROW_REFERENCE
    ): Either[String, Option[ARROW]] =

      f(network, arrowReference)
