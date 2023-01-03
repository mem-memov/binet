package net.mem_memov.binet.hexagon.general.target

trait CreateArrowFromSource[TARGET, ADDRESS, ARROW, NETWORK]:

  def f(
    target: TARGET,
    sourceDotAddress: ADDRESS,
    sourceArrowAddressOption: Option[ADDRESS],
    network: NETWORK
  ): Either[String, (NETWORK, ARROW)]

  extension (target: TARGET)

    def createArrowFromSource(
      sourceDotAddress: ADDRESS,
      sourceArrowAddressOption: Option[ADDRESS],
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)]=

      f(target, sourceDotAddress, sourceArrowAddressOption, network)
