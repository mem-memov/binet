package net.mem_memov.binet.hexagon.general.source

trait DeleteArrowToTarget[SOURCE, NETWORK, TARGET]:

  def f(
    source: SOURCE,
    target: TARGET,
    network: NETWORK
  ): Either[String, NETWORK]

  extension (source: SOURCE)

    def deleteArrowToTarget(
      target: TARGET,
      network: NETWORK
    ): Either[String, NETWORK] =

      f(source, target, network)
