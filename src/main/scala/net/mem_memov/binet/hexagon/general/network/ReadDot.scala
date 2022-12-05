package net.mem_memov.binet.hexagon.general.network

trait ReadDot[NETWORK, ADDRESS]:

  def f(
    network: NETWORK,
    address: ADDRESS
  ): Either[String, NETWORK]

  extension (network: NETWORK)

    def readDot(
      address: ADDRESS
    ): Either[String, NETWORK] =

      f(network, address)
