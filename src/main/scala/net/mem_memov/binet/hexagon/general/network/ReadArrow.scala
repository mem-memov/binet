package net.mem_memov.binet.hexagon.general.network

trait ReadArrow[NETWORK, ADDRESS]:

  def f(
    network: NETWORK,
    address: ADDRESS
  ): Either[String, NETWORK]

  extension (network: NETWORK)

    def readArrow(
      address: ADDRESS
    ): Either[String, NETWORK] =

      f(network, address)
