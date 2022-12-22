package net.mem_memov.binet.hexagon.general.tail

trait CollectSources[TAIL, NETWORK, SOURCE]:

  def f(
    tail: TAIL,
    network: NETWORK,
    sources: List[SOURCE]
  ): Either[String, List[SOURCE]]

  extension (tail: TAIL)

    def collectSources(
      network: NETWORK,
      sources: List[SOURCE]
    ): Either[String, List[SOURCE]] =

      f(tail, network, sources)
