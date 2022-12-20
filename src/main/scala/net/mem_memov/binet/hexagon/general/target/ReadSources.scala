package net.mem_memov.binet.hexagon.general.target

trait ReadSources[TARGET, NETWORK, SOURCE]:

  def f(
    target: TARGET,
    network: NETWORK
  ): Either[String, List[SOURCE]]

  extension (target: TARGET)

    def readSources(
      network: NETWORK
    ): Either[String, List[SOURCE]] =

      f(target, network)
