package net.mem_memov.binet.hexagon.general.network

trait UpdateArrow[NETWORK, ARROW]:

  def f(
    network: NETWORK,
    arrow: ARROW
  ): Either[String, NETWORK]

  extension (network: NETWORK)

    def updateArrow(
      arrow: ARROW
    ): Either[String, NETWORK] =

      f(network, arrow)
