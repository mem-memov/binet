package net.mem_memov.binet.hexagon.general.predecessor

trait ReadSuccessors[PREDECESSOR, NETWORK, SUCCESSOR]:

  def f(
    predecessor: PREDECESSOR,
    network: NETWORK
  ): Either[String, Vector[SUCCESSOR]]

  extension (predecessor: PREDECESSOR)

    def readSuccessors(
      network: NETWORK
    ): Either[String, Vector[SUCCESSOR]] =

      f(predecessor, network)
