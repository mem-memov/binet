package net.mem_memov.binet.hexagon.general.network

trait CreateArrow[NETWORK, ADDRESS, ARROW]:

  def f(
    network: NETWORK,
    sourceAddress: ADDRESS,
    targetAddress: ADDRESS
  ): Either[String, (NETWORK, ARROW)]

  extension (network: NETWORK)

    def createArrow(
      sourceAddress: ADDRESS,
      targetAddress: ADDRESS
    ): Either[String, (NETWORK, ARROW)] =

      f(network, sourceAddress, targetAddress)
