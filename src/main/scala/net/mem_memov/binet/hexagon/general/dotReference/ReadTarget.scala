package net.mem_memov.binet.hexagon.general.dotReference

trait ReadTarget[DOT_REFERENCE, NETWORK, TARGET]:

  def f(
    dotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, TARGET]

  extension (dotReference: DOT_REFERENCE)

    def readTarget(
      network: NETWORK
    ): Either[String, TARGET] =

      f(dotReference, network)
