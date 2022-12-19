package net.mem_memov.binet.hexagon.general.target

trait CreateArrowFromSource[TARGET, ARROW, ENTRY, NETWORK]:

  def f(
    target: TARGET,
    entry: ENTRY,
    network: NETWORK
  ): Either[String, (NETWORK, ARROW)]

  extension (target: TARGET)

    def createArrowFromSource(
      entry: ENTRY,
      network: NETWORK
    ): Either[String, (NETWORK, ARROW)] =

      f(target, entry, network)
