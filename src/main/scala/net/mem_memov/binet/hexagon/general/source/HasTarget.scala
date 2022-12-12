package net.mem_memov.binet.hexagon.general.source

trait HasTarget[SOURCE, NETWORK, TARGET]:

  def f(
    source: SOURCE,
    target: TARGET,
    network: NETWORK
  ): Boolean

  extension (source: SOURCE)

    def hasTarget(
      target: TARGET,
      network: NETWORK
    ): Boolean =

      f(source, target, network)
