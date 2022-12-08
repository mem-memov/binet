package net.mem_memov.binet.hexagon.general.network

trait ReadDot[NETWORK, ADDRESS, DOT]:

  def f(
    network: NETWORK,
    address: ADDRESS
  ): Either[String, DOT]

  extension (network: NETWORK)

    def readDot(
      address: ADDRESS
    ): Either[String, DOT] =

      f(network, address)
