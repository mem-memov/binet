package net.mem_memov.binet.hexagon.general.target

trait HasSource[TARGET, NETWORK, SOURCE]:

  def f(
    target: TARGET,
    source: SOURCE,
    network: NETWORK
  ): Either[String, Boolean]

  extension (target: TARGET)

    def hasSource(
      source: SOURCE,
      network: NETWORK
    ): Either[String, Boolean] =

      f(target, source, network)