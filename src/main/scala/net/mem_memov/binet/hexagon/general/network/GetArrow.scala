package net.mem_memov.binet.hexagon.general.network

trait GetArrow[NETWORK, ARROW]:

  def f(
    network: NETWORK
  ): Either[String, ARROW]

  extension (network: NETWORK)

    def getArrow(): Either[String, ARROW] =

      f(network)
