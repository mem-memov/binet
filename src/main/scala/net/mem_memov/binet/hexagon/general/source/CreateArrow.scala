package net.mem_memov.binet.hexagon.general.source

trait CreateArrow[SOURCE, NETWORK, TARGET]:

  def f(
    source: SOURCE,
    target: TARGET,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (source: SOURCE)

    def createArrow(
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(source, target, network)

