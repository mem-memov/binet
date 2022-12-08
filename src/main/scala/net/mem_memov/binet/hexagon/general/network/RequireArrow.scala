package net.mem_memov.binet.hexagon.general.network

trait RequireArrow[NETWORK, ARROW]:

  def f(
    network: NETWORK
  ): Either[String, ARROW]

  extension (network: NETWORK)

    def requireArrow(): Either[String, ARROW] =

      f(network)
