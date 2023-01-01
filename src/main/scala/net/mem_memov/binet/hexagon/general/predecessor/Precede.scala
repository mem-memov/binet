package net.mem_memov.binet.hexagon.general.predecessor

trait Precede[PREDECESSOR, DOT, NETWORK]:

  def f(
    predecessor: PREDECESSOR,
    successorDot: DOT,
    network: NETWORK
  ): Either[String, (NETWORK, PREDECESSOR)]

  extension (predecessor: PREDECESSOR)

    def precede(
      successorDot: DOT,
      network: NETWORK
    ): Either[String, (NETWORK, PREDECESSOR)] =

      f(predecessor, successorDot, network)
