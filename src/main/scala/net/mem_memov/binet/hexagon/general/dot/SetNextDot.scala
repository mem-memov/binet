package net.mem_memov.binet.hexagon.general.dot

trait SetNextDot[DOT, DOT_REFERENCE, NETWORK]:

  def f(
    dot: DOT,
    identifierDotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, DOT)]

  extension (dot: DOT)

    def setNextDot(
      identifierDotReference: DOT_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, DOT)] =

      f(dot, identifierDotReference, network)
