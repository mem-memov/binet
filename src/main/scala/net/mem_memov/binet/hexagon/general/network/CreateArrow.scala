package net.mem_memov.binet.hexagon.general.network

trait CreateArrow[NETWORK, ARROW, ENTRY]:

  def f(
    network: NETWORK,
    entry: ENTRY
  ): Either[String, (NETWORK, ARROW)]

  extension (network: NETWORK)

    def createArrow(
      entry: ENTRY
    ): Either[String, (NETWORK, ARROW)] =

      f(network, entry)
