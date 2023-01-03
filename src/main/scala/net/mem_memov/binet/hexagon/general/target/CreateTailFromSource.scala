package net.mem_memov.binet.hexagon.general.target

trait CreateTailFromSource[TARGET, ADDRESS, NETWORK, TAIL]:

  def f(
    target: TARGET,
    sourceDotAddress: ADDRESS,
    sourceArrowAddressOption: Option[ADDRESS],
    network: NETWORK
  ): Either[String, (NETWORK, TAIL)]

  extension (target: TARGET)

    def createTailFromSource(
      sourceDotAddress: ADDRESS,
      sourceArrowAddressOption: Option[ADDRESS],
      network: NETWORK
    ): Either[String, (NETWORK, TAIL)]=

      f(target, sourceDotAddress, sourceArrowAddressOption, network)
