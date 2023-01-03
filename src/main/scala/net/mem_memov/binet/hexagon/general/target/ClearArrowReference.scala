package net.mem_memov.binet.hexagon.general.target

trait ClearArrowReference[TARGET, NETWORK]:

  def f(
    target: TARGET,
    network: NETWORK
  ): Either[String, (NETWORK, TARGET)]

  extension (target: TARGET)

    def clearArrowReference(
      network: NETWORK
    ): Either[String, (NETWORK, TARGET)] =

      f(target, network)
