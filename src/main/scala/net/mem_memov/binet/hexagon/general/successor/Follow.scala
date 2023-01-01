package net.mem_memov.binet.hexagon.general.successor

trait Follow[SUCCESSOR, NETWORK, PREDECESSOR]:

  def f(
    successor: SUCCESSOR,
    predecessor: PREDECESSOR,
    network: NETWORK
  ): Either[String, (NETWORK, PREDECESSOR)]

  extension (successor: SUCCESSOR)

    def follow(
      predecessor: PREDECESSOR,
      network: NETWORK
    ): Either[String, (NETWORK, PREDECESSOR)] =

      f(successor, predecessor, network)
