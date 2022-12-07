package net.mem_memov.binet.hexagon.general.network

trait CreateArrow[NETWORK, ADDRESS]:

  def f(
    network: NETWORK,
    sourceAddress: ADDRESS,
    targetAddress: ADDRESS
  ): Either[String, NETWORK]

  extension (network: NETWORK)

    def createArrow(
      sourceAddress: ADDRESS,
      targetAddress: ADDRESS
    ): Either[String, NETWORK] =

      f(network, sourceAddress, targetAddress)