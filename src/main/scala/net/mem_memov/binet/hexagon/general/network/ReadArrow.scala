package net.mem_memov.binet.hexagon.general.network

trait ReadArrow[NETWORK, ADDRESS, ARROW]:

  def f(
    network: NETWORK,
    address: ADDRESS
  ): Either[String, ARROW]

  extension (network: NETWORK)

    def readArrow(
      address: ADDRESS
    ): Either[String, ARROW] =

      f(network, address)
