package net.mem_memov.binet.hexagon.general.target

trait CreateArrowFromSource[TARGET, ADDRESS, ARROW, NETWORK]:

  def f(
    target: TARGET,
    sourceAddress: ADDRESS,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW)]

  extension (target: TARGET)

    def createArrowFromSource(
      sourceAddress: ADDRESS,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      f(target, sourceAddress, network)
