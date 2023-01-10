package net.mem_memov.binet.hexagon.general.predecessor

trait Precede[PREDECESSOR, DOT_REFERENCE, NETWORK]:

  def f(
    predecessor: PREDECESSOR,
    successorDotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, PREDECESSOR)]

  extension (predecessor: PREDECESSOR)

    def precede(
      successorDotReference: DOT_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, PREDECESSOR)] =

      f(predecessor, successorDotReference, network)
