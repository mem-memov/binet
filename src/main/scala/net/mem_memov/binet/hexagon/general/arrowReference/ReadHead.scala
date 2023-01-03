package net.mem_memov.binet.hexagon.general.arrowReference

trait ReadHead[ARROW_REFERENCE, HEAD, NETWORK]:

  def f(
    arrowReference: ARROW_REFERENCE,
    network: NETWORK
  ): Either[String, Option[HEAD]]

  extension (arrowReference: ARROW_REFERENCE)

    def readHead(
      network: NETWORK
    ): Either[String, Option[HEAD]] =

      f(arrowReference, network)
