package net.mem_memov.binet.hexagon.general.network

trait CreateArrow[NETWORK, ADDRESS, ARROW]:

  def f(
    network: NETWORK,
    sourceDotAddress: ADDRESS,
    sourceArrowAddressOption: Option[ADDRESS],
    targetDotAddress: ADDRESS,
    targetArrowAddressOption: Option[ADDRESS]
  ): Either[String, (NETWORK, ARROW)]

  extension (network: NETWORK)

    def createArrow(
      sourceDotAddress: ADDRESS,
      sourceArrowAddressOption: Option[ADDRESS],
      targetDotAddress: ADDRESS,
      targetArrowAddressOption: Option[ADDRESS]
    ): Either[String, (NETWORK, ARROW)] =

      f(network, sourceDotAddress, sourceArrowAddressOption, targetDotAddress, targetArrowAddressOption)
