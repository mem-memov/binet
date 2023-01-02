package net.mem_memov.binet.hexagon.general.target

trait DeleteArrowToSource[TARGET, NETWORK, SOURCE]:

  def f(
    target: TARGET,
    source: SOURCE,
    network: NETWORK
  ): Either[String, (NETWORK, TARGET)]

  extension (target: TARGET)

    def deleteArrowToSource(
      source: SOURCE,
      network: NETWORK
    ): Either[String, (NETWORK, TARGET)] =

      f(target, source, network)
