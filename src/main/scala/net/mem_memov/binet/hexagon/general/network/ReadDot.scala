package net.mem_memov.binet.hexagon.general.network

trait ReadDot[NETWORK, DOT, DOT_REFERENCE]:

  def f(
    network: NETWORK,
    dotReference: DOT_REFERENCE
  ): Either[String, DOT]

  extension (network: NETWORK)

    def readDot(
      dotReference: DOT_REFERENCE
    ): Either[String, DOT] =

      f(network, dotReference)
