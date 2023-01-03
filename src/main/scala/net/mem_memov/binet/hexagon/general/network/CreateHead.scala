package net.mem_memov.binet.hexagon.general.network

trait CreateHead[NETWORK, ADDRESS, HEAD]:

  def f(
    network: NETWORK,
    sourceDotAddress: ADDRESS,
    sourceArrowAddressOption: Option[ADDRESS],
    targetDotAddress: ADDRESS,
    targetArrowAddressOption: Option[ADDRESS]
  ): Either[String, (NETWORK, HEAD)]

  extension (network: NETWORK)

    def createHead(
      sourceDotAddress: ADDRESS,
      sourceArrowAddressOption: Option[ADDRESS],
      targetDotAddress: ADDRESS,
      targetArrowAddressOption: Option[ADDRESS]
    ): Either[String, (NETWORK, HEAD)] =

      f(network, sourceDotAddress, sourceArrowAddressOption, targetDotAddress, targetArrowAddressOption)
