package net.mem_memov.binet.hexagon.general.source

trait IsInTails[SOURCE, NETWORK, TAIL]:

  def f(
    source: SOURCE,
    tail: TAIL,
    network: NETWORK
  ): Either[String, Boolean]

  extension (source: SOURCE)

    def isInTails(
      tail: TAIL,
      network: NETWORK
    ): Either[String, Boolean] =

      f(source, tail, network)
