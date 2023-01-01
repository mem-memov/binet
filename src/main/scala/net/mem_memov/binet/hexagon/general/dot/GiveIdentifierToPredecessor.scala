package net.mem_memov.binet.hexagon.general.dot

trait GiveIdentifierToPredecessor[DOT, NETWORK]:

  def f(
    dot: DOT,
    predecessorDot: DOT,
    network: NETWORK
  ): Either[String, (NETWORK, DOT)]

  extension (dot: DOT)

    def giveIdentifierToPredecessor(
      predecessorDot: DOT,
      network: NETWORK
    ): Either[String, (NETWORK, DOT)] =

      f(dot, predecessorDot, network)
