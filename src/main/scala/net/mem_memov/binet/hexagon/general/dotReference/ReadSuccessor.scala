package net.mem_memov.binet.hexagon.general.dotReference

trait ReadSuccessor[DOT_REFERENCE, NETWORK, SUCCESSOR]:

  def f(
    dotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, Option[SUCCESSOR]]

  extension (dotReference: DOT_REFERENCE)

    def readSuccessor(
      network: NETWORK
    ): Either[String, Option[SUCCESSOR]] =

      f(dotReference, network)
