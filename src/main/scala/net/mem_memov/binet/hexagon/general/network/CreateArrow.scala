package net.mem_memov.binet.hexagon.general.network

trait CreateArrow[NETWORK, ARROW]:

  def f(
    network: NETWORK
  ): Either[String, NETWORK]

  extension (network: NETWORK)

    def createArrow(): Either[String, NETWORK] =

      f(network)