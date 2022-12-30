package net.mem_memov.binet.hexagon.general.source

trait CreateArrowToTarget[SOURCE, NETWORK, TARGET]:

  def f(
    source: SOURCE,
    target: TARGET,
    network: NETWORK
  ): Either[String, (NETWORK, SOURCE, TARGET)]

  extension (source: SOURCE)

    def createArrowToTarget(
      target: TARGET,
      network: NETWORK
    ): Either[String, (NETWORK, SOURCE, TARGET)] =

      f(source, target, network)

