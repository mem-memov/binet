package net.mem_memov.binet.hexagon.general.target

trait CreateArrow[TARGET, NETWORK, ADDRESS]:

  def f(
    target: TARGET,
    sourceAddress: ADDRESS,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (target: TARGET)

    def createArrow(
      sourceAddress: ADDRESS,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(target, sourceAddress, network)
