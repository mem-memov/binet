package net.mem_memov.binet.hexagon.general.source

trait ClearArrowReference[SOURCE, NETWORK]:

  def f(
    source: SOURCE,
    network: NETWORK
  ): Either[String, (NETWORK, SOURCE)]

  extension (source: SOURCE)

    def clearArrowReference(
      network: NETWORK
    ): Either[String, (NETWORK, SOURCE)] =

      f(source, network)
