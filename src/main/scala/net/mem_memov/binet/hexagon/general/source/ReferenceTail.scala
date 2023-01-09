package net.mem_memov.binet.hexagon.general.source

trait ReferenceTail[SOURCE, DOT_REFERENCE, NETWORK]:

  def f(
    source: SOURCE,
    dotReference: DOT_REFERENCE,
    network: NETWORK
  ): Either[String, (NETWORK, SOURCE)]

  extension (source: SOURCE)

    def referenceHead(
      dotReference: DOT_REFERENCE,
      network: NETWORK
    ): Either[String, (NETWORK, SOURCE)] =

      f(source, dotReference, network)
