package net.mem_memov.binet.hexagon.general.source

trait ReadTargets[SOURCE, NETWORK, TARGET]:

  def f(
    source: SOURCE,
    network: NETWORK
  ): Either[String, List[TARGET]]

  extension (source: SOURCE)

    def readTargets(
      network: NETWORK
    ): Either[String, List[TARGET]] =

      f(source, network)
