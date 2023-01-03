package net.mem_memov.binet.hexagon.general.arrowReference

trait ReadTail[ARROW_REFERENCE, NETWORK, TAIL]:

  def f(
    arrowReference: ARROW_REFERENCE,
    network: NETWORK
  ): Either[String, Option[TAIL]]

  extension (arrowReference: ARROW_REFERENCE)

    def readTail(
      network: NETWORK
    ): Either[String, Option[TAIL]] =

      f(arrowReference, network)
